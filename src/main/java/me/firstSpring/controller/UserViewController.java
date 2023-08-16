package me.firstSpring.controller;

import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.User;
import me.firstSpring.service.UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.Authentication;


@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    @GetMapping("sign-up")
    public String showUserProfile(Authentication authentication, Model model) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        if(oAuth2User != null)
            return "login";
        String userEmail = (String) oAuth2User.getAttribute("email");

        // userEmail을 사용하여 사용자 정보를 가져옵니다.
        User user = userService.findByEmail(userEmail);

        model.addAttribute("users", user); // user 객체를 모델에 추가하여 뷰로 전달
        return "signup"; // user/profile.html 템플릿을 렌더링합니다.
    }

}
