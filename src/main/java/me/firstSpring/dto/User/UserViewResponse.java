package me.firstSpring.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UserViewResponse {
    private Long id;
    private String email;
    private String password;
    private String nickname;
    private Long age;
    private Long weight;
    private Long tall;

    public UserViewResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.age = user.getAge();
        this.weight = user.getWeight();
        this.tall = user.getTall();
    }
}
