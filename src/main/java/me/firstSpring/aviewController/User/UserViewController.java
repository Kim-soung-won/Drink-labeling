package me.firstSpring.aviewController.User;

import lombok.RequiredArgsConstructor;
import me.firstSpring.apiController.User.UserApiController;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

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
    @GetMapping("/api/signup")
    public RedirectView signup(){
        System.out.println("return OK");
        RedirectView redirectView = new RedirectView("/signup");
        return redirectView;
    }
    @GetMapping("/signup")
    public String redirectToAnotherPage() {
        // 다른 페이지로 리다이렉트
        return "User/signup";
    }

    @GetMapping("/userDataPage")
    public String getUserPage(){
        return "User/user";
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
