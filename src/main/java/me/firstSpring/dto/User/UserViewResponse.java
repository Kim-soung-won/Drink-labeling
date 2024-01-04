package me.firstSpring.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Enum.Roles;
import me.firstSpring.domain.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UserViewResponse {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Roles role;

    public UserViewResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }
}
