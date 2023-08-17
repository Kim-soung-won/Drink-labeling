package me.firstSpring.controller;

import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.User;
import me.firstSpring.dto.ArticleListViewResponse;
import me.firstSpring.dto.UserListViewResponse;
import me.firstSpring.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private UserService userService;
    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/user")
    public String getUser(Model model){ //Model : HTML쪽으로 값을 넘겨주는 객체
        List<UserListViewResponse> articles = userService.findAll().stream()
                .map(UserListViewResponse::new)
                .toList();
        model.addAttribute("user",articles);

        return "user";
    }


}
