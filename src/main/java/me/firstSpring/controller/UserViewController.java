package me.firstSpring.controller;

import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.User;
import me.firstSpring.dto.UserViewResponse;
import me.firstSpring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/sign-up")
    //id 키를 가진 쿼리 파라미터의 값을 id변수에 매핑
    public String newUser(Model model, Principal principal) {
        if (principal instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal oAuth2Principal = (OAuth2AuthenticatedPrincipal) principal;
            String email = oAuth2Principal.getAttribute("email"); // Assuming email attribute exists in the principal

            User user = userService.findByEmail(email);

            if (user == null) { // 사용자 정보가 없으면 새로 생성
                model.addAttribute("users", new UserViewResponse());
            } else { // 사용자 정보가 있으면 수정
                model.addAttribute("users", new UserViewResponse(user));
            }
        } else {
            return "login";
        }
        return"signup";
    }


}
