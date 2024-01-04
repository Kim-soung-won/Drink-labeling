package me.firstSpring.config.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.ReFreshToken;
import me.firstSpring.domain.User;
import me.firstSpring.repository.RefreshTokenRepository;
import me.firstSpring.service.UserDetailService;
import me.firstSpring.service.UserService;
import me.firstSpring.util.CookieUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "/successLogin";
    public static final String SIGNUP_PATH = "/signup";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final UserService userService;
    private final UserDetailService userDetailService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException{
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User user = userService.findByEmail((String) oAuth2User.getAttributes().get("email"));
        System.out.println("USER IS SAVE"+user.getId());

        if(userDetailService.findByUser(user)==null){ //최초 로그인 시도시 유저 상세정보 작성 페이지로
            System.out.println("널이맞구요..");
            String signup = UriComponentsBuilder.fromUriString(SIGNUP_PATH)
                    .queryParam("FJEOFEONVISAKODOAJ",user.getId())
                    .build()
                    .toUriString();
            response.sendRedirect(signup);
            return;
        }

        //네이버 OAuth 안되는 구간 .get("email")로 값을 받아오기 전에 json response 처리를 우선해야함
        //리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
        String refreshToken = tokenProvider.generateToken(user,REFRESH_TOKEN_DURATION); //기간을 담아서 새로운 토큰 생성
        saveRefreshToken(user.getId(),refreshToken); //토큰을 refresh_token테이블에 저장
        addRefreshTokenToCookie(request,response,refreshToken); //브라우저에 있는 기존 토큰 삭제, 새 토큰 추가

        //액세스 토큰 생성 -> 패스에 액세스 토큰 추가
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);

        //인증 관련 설정 값, 쿠키 제거
        clearAuthenticationAttributes(request,response);
        //리다이렉트
        getRedirectStrategy().sendRedirect(request,response,targetUrl);
    }

    //생성된 리프레시 토큰을 전달받아 데이터베이스에 저장
    private void saveRefreshToken(Long userId, String newRefreshToken){
        ReFreshToken reFreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken)) //기존값이 있을 경우 새Entity로 업데이트
                .orElse(new ReFreshToken(userId, newRefreshToken)); //없으면 새로 생성

        refreshTokenRepository.save(reFreshToken);
    }

    //생성된 리프레시 토큰을 쿠키에 저장
    private void addRefreshTokenToCookie(HttpServletRequest request,HttpServletResponse response,
                                         String refreshToken){
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request,response,REFRESH_TOKEN_COOKIE_NAME); //기존에 있던 쿠키 삭제
        CookieUtil.addCookie(response,REFRESH_TOKEN_COOKIE_NAME,refreshToken,cookieMaxAge); //새로 받은 쿠키 추가
    }

    //인증 관련 설정값, 쿠키 제거
    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response){
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request,response);
    }

    //액세스 토큰을 패스에 추가
    private String getTargetUrl(String token){
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token",token)
                .build()
                .toUriString();
    }
}
