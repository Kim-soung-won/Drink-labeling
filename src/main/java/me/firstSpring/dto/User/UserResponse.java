package me.firstSpring.dto.User;

import lombok.Getter;
import me.firstSpring.domain.Article;
import me.firstSpring.domain.User;

@Getter
public class UserResponse {
    private final String email;
    private final String nickname;
    public UserResponse(User user){
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
