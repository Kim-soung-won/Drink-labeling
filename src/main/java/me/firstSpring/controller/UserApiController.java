package me.firstSpring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.Article;
import me.firstSpring.domain.User;
import me.firstSpring.dto.Article.UpdateArticleRequest;
import me.firstSpring.dto.User.AddUserRequest;
import me.firstSpring.dto.User.UpdateUserRequest;
import me.firstSpring.dto.User.UserResponse;
import me.firstSpring.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;
    private final TokenProvider tokenProvider;


    @PostMapping("/login")
    public String signup(AddUserRequest request){
        userService.save(request); //회원가입 메서드 호출
        return "redirect:/oauthLogin"; //회원가입이 완료된 이후 로그인 페이지로 이동
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/oauthLogin";
    }
    @PutMapping("/api/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id,
                                                @RequestBody UpdateUserRequest request){
        Long aa = tokenProvider.getUserId(id);
        User updateUser = userService.update(aa,request);

        return ResponseEntity.ok().body(updateUser);
    }
}
