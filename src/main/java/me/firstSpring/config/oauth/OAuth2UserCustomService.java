package me.firstSpring.config.oauth;

//리소스 서버에서 보내주는 사용자 정보를 불러와 조회하고 정보가 있다면 업데이트 없다면 추가

import lombok.RequiredArgsConstructor;
import me.firstSpring.config.oauth.portal.FaceBookUserInfo;
import me.firstSpring.config.oauth.portal.GoogleUserInfo;
import me.firstSpring.config.oauth.portal.NaverUserInfo;
import me.firstSpring.domain.User;
import me.firstSpring.repository.OAuth2UserInfo;
import me.firstSpring.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService; //OAuth2인증 시 사용자 정보를 가져옴
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    //extends == 상속
    //부모클래스 DefaultOAuth2UserService에서 제공하는 정보로 loadUser()메서드를 오버라이딩해 객체를 불러온다
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest : "+userRequest.getClientRegistration());
        System.out.println("userRequestAccessToken : "+userRequest.getAccessToken());
        System.out.println("getAttribute : "+super.loadUser(userRequest).getAttributes());
        //요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest);
        System.out.println("user : "+user);
        saveOrUpdate(user, userRequest);
        return user;
    }

    //유저 정보를 저장하는 메서드
    private User saveOrUpdate(OAuth2User oAuth2User, OAuth2UserRequest oAuth2UserRequest){
        OAuth2UserInfo oAuth2UserInfo = null;
        if(oAuth2UserRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if(oAuth2UserRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
        }
        else if(oAuth2UserRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }
        System.out.println("type : "+ oAuth2UserInfo.getEmail());

        String email = (String) oAuth2UserInfo.getEmail();
        String name = (String) oAuth2UserInfo.getName();
        String provider = oAuth2UserInfo.getProvider();
        String provider_id= oAuth2UserInfo.getProviderId();
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .role("USER")
                        .provider(provider)
                        .provider_id(provider_id)
                        .build());
        System.out.println("User : "+ user);
        return userRepository.save(user);
    }
}
