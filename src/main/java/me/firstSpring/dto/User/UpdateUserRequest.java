package me.firstSpring.dto.User;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserRequest {
    private Long age;
    private Long weight;
    private Long tall;
}