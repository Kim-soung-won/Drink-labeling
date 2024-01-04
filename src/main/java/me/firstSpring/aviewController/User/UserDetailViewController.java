package me.firstSpring.aviewController.User;

import lombok.RequiredArgsConstructor;
import me.firstSpring.repository.UserDetailBasicRepository;
import me.firstSpring.service.UserService;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class UserDetailViewController {
    private final UserDetailBasicRepository userDetailBasicRepository;
    private final UserService userService;

}
