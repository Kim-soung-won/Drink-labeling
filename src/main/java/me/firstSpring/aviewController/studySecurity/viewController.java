package me.firstSpring.aviewController.studySecurity;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@RequiredArgsConstructor
@Controller
public class viewController {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping("/loginForm")
    public String loginForm(){

        return "test/loginform";
    }

    @GetMapping("/joinForm")
    public String joinForm(){

        return "test/joinForm";
    }
    @PreAuthorize("hasRole('MANAGER') or hasRole('admin')")
    @GetMapping("/info")
    public String info(){
        return "userinfo";
    }
}
