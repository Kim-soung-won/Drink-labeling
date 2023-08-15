package me.firstSpring.controller;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.User;
import me.firstSpring.dto.UserViewResponse;
import me.firstSpring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
@Controller
public class UserViewController {
    private final UserService userService;
    @GetMapping("/login")
    public String login(){
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("/sign-up")
    //id 키를 가진 쿼리 파라미터의 값을 id변수에 매핑
    public String newUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String userEmail = userDetails.getUsername(); // 현재 로그인된 사용자의 이메일

        User user = userService.findByEmail(userEmail);
        if(user == null){ //id가 없으면 생성
            model.addAttribute("users", new UserViewResponse());
        } else{ //있으면 수정
            model.addAttribute("users", new UserViewResponse(user));
        }
        return "signup";
    }
}
