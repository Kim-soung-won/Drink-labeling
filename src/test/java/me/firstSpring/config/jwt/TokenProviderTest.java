package me.firstSpring.config.jwt;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import me.firstSpring.domain.User;
import me.firstSpring.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    //generateToken 검증 테스트
    @DisplayName("generateToken() : 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken(){
        //given 토큰에 유저 정보를 추가하기 위한 테스트 유저를 만든다.
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        //when 토큰 제공자의 generateToken()메서드를 호출해 토큰을 만든다.
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        //then jjwt라이브러리를 사용해 토큰을 복호화한다. id값이 given절에서 만든 userid와 동일한지 확인한다.
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id",Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validToken() : 만료된 토큰인 때에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken(){
        //given jjwt라이브러리를 사용해 토큰을 생성한다.
        String token = JwtFactory.builder().
                expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()) //이미 만료된 토큰을 생성한다.
        ).build().createToken(jwtProperties);

        //when 유효한 토큰인지 확인한다.
        boolean result = tokenProvider.validToken(token);

        //then 결과가 False인지 확인한다.
        assertThat(result).isFalse();
    }

    @DisplayName("validToken() : 유효한 토큰인 때에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken(){
        //given 그냥 토큰을 하나 만든다.
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        //when 유효한 토큰인지 확인한다
        boolean result = tokenProvider.validToken(token);

        //then True인지 확인한다.
        assertThat(result).isTrue();
    }

    @DisplayName("getAuthentication() : 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication(){
        String userEmail = "user@email.com";
        //given jjwt라이브러리를 사용해 유저이메일을 담은 토큰을 만든다.
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        //when 인증 객체를 반환받는다.
        Authentication authentication = tokenProvider.getAuthentication(token);

        //then 인증객체의 유저이름을 가져와 같은지 확인한다.
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    @DisplayName("getUserId() : 토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getUserId(){
        //given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id",userId))
                .build()
                .createToken(jwtProperties);

        //when
        Long userIdByToken = tokenProvider.getUserId(token);

        //then
        assertThat(userIdByToken).isEqualTo(userId);
    }
}