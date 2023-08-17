package me.firstSpring.config.oauth;

//리소스 서버에서 보내주는 사용자 정보를 불러와 조회하고 정보가 있다면 업데이트 없다면 추가

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.User;
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
        //요청을 바탕으로 유저 정보를 담은 객체 반환
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    //유저 정보를 저장하는 메서드
    private User saveOrUpdate(OAuth2User oAuth2User){
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(User.builder()
                        .email(email)
                        .nickname(name)
                        .build());
        return userRepository.save(user);
    }
}
