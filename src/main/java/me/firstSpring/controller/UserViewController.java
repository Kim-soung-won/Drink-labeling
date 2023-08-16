package me.firstSpring.controller;

import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.User;
import me.firstSpring.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private UserService userService;
    private TokenProvider tokenProvider;
    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/sign-up")
    public String newUsers(Authentication authentication, Model model){
        if (authentication != null && authentication.isAuthenticated()) {
            String tokenValue = (String) authentication.getCredentials();

            Long userId = tokenProvider.getUserId(tokenValue);

            User user = userService.findById(userId);
            model.addAttribute("users", user);
        }
        return "signup";
    }
}
