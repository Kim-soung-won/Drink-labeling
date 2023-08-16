package me.firstSpring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.Article;
import me.firstSpring.domain.User;
import me.firstSpring.dto.*;
import me.firstSpring.repository.UserRepository;
import me.firstSpring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    private final TokenProvider tokenProvider;

    @PostMapping("/user")
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
    @PutMapping("/api/signup/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id,
                                                @RequestBody UpdateUserRequest request){
        User updateUser = userService.update(id,request);

        return ResponseEntity.ok().body(updateUser);
    }
//    @PostMapping("/api/user")
//    //@RequestBody로 요청 본문 값 매핑
//    public ResponseEntity<User> addUser(@RequestBody AddUserRequest request,
//                                              Principal principal){
//        User savedUser = userService.save(request,principal.getName());
//
//        //요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
//    }

}
