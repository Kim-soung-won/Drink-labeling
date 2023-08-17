package me.firstSpring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Article;
import me.firstSpring.domain.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UserListViewResponse {
    private Long id;
    private String email;

    public UserListViewResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
    }
}