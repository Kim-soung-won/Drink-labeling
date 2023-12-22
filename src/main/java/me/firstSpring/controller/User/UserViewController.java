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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final UserService userService;

    public UserApiController userApiController;

    private final TokenProvider tokenProvider;

//
//    @GetMapping("/login")
//    public String login() {
//        return "oauthLogin";
//    }
    @GetMapping("/successLogin")
    public String tokenAccess(){
        return "tokenAccessPage";
    }
    @GetMapping("/signup")
    public String signup() {
        return "User/signup";
    }

    @GetMapping("/userDataPage")
    public String getUserPage(){
        return "user";
    }

//    @GetMapping("/user/{id}")
//    public String getUserid(@PathVariable Long id, Model model){
//        User user = userService.findById(id);
//        System.out.println(user.getAge());
//        model.addAttribute("user",new UserViewResponse(user));
//
//        return "user";
//    }

}
