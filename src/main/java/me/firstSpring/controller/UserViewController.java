package me.firstSpring.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.JwtProperties;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.Article;
import me.firstSpring.domain.User;
import me.firstSpring.dto.Article.ArticleResponse;
import me.firstSpring.dto.Article.ArticleViewResponse;
import me.firstSpring.dto.User.UserListViewResponse;
import me.firstSpring.dto.User.UserResponse;
import me.firstSpring.dto.User.UserViewResponse;
import me.firstSpring.service.BlogService;
import me.firstSpring.service.UserDetailService;
import me.firstSpring.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final UserService userService;

    public UserApiController userApiController;


    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/user")
    public String getUsers(Principal principal, Model model){
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user",new UserViewResponse(user));

        return "user";
    }
    @GetMapping("/user/{id}")
    public String getUserid(@PathVariable Long id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user",new UserViewResponse(user));

        return "user";
    }
}
