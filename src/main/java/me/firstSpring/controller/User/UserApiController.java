package me.firstSpring.controller.User;

import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.User;
import me.firstSpring.dto.User.UserViewResponse;
import me.firstSpring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @GetMapping("/api/userData")
    public ResponseEntity<UserViewResponse> getUserData(Authentication authentication){
        if(authentication != null) {
            System.out.println("http Auth : " + authentication.getName());
            User user = userService.findByEmail(authentication.getName());
            System.out.println("http user : " + user);
            return ResponseEntity.ok(new UserViewResponse(user));
        }
        else{
            System.out.println("http Auth null");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

//    @PostMapping("/login")
//    public String signup(AddUserRequest request){
//        userService.save(request); //회원가입 메서드 호출
//        return "redirect:/oauthLogin"; //회원가입이 완료된 이후 로그인 페이지로 이동
//    }

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response){
//        new SecurityContextLogoutHandler().logout(request, response,
//                SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/oauthLogin";
//    }
}
