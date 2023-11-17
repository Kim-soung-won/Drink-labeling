package me.firstSpring.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.User;

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