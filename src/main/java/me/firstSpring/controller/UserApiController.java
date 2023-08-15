package me.firstSpring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.User;
import me.firstSpring.dto.AddUserRequest;
import me.firstSpring.dto.UserViewResponse;
import me.firstSpring.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

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
    @GetMapping("/sign-up")
    //id 키를 가진 쿼리 파라미터의 값을 id변수에 매핑
    public String newUser(@RequestParam(required = false) Long id, Model model){
        if(id == null){ //id가 없으면 생성
            model.addAttribute("users", new UserViewResponse());
        } else{ //있으면 수정
            User user = userService.findById(id);
            model.addAttribute("users", new UserViewResponse(user));
        }
        return "signup";
    }
}
