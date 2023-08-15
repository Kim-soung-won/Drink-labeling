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

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String userEmail = userDetails.getUsername();

                User user = userService.findByEmail(userEmail);

                if (user == null) { // 사용자 정보가 없으면 새로 생성
                    model.addAttribute("users", new UserViewResponse());
                } else { // 사용자 정보가 있으면 수정
                    model.addAttribute("users", new UserViewResponse(user));
                }
            }
        }
        return "signup";
    }
}
