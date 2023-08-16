package me.firstSpring.controller;

import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.User;
import me.firstSpring.service.UserService;
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
        if (authentication != null && authentication.isAuthenticated()) {
            String userEmail = authentication.getName();
            User user = userService.findByEmail(userEmail);
            model.addAttribute("user", user);
            return "signup"; // user/profile.html 템플릿을 렌더링합니다.
        } else {
            // 인증되지 않은 상태에 대한 처리
            return "login"; // 로그인 페이지로 리다이렉션
        }
    }


}
