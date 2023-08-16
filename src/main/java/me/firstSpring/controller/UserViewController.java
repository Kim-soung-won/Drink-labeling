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
        User user = userService.findByEmail((String) oAuth2User.getAttributes().get("email"));
        model.addAttribute("users", user);
        return "signup"; // user/profile.html 템플릿을 렌더링합니다.
    }


}
