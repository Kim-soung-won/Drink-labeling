package me.firstSpring.dto.UserDetailBasic;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import me.firstSpring.domain.User;

@Getter
@Setter
public class AddUserDetailRequest {
    private String name;
    private String birth;
    private String sex;
    private String phoneNumber;
    private String email;
    private String address;
    private String cardNum;
    private Long user_id;
}
