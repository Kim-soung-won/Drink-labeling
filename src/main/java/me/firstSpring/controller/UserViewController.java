package me.firstSpring.controller;

import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.User;
import me.firstSpring.dto.UserViewResponse;
import me.firstSpring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.security.Principal;

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
        String userEmail = authentication.getName();
        User user = userService.findByEmail(userEmail);
        model.addAttribute("users", user);
        return "signup"; // user/profile.html 템플릿을 렌더링합니다.
    }


}
