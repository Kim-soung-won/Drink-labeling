package me.firstSpring.dto.UserDetailBasic;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import me.firstSpring.domain.Orders;
import me.firstSpring.domain.User;
import me.firstSpring.domain.UserDetailBasic;

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

    public UserDetailBasic toEntity(User user){
        return UserDetailBasic.builder()
                .name(name)
                .birth(birth)
                .sex(sex)
                .phoneNumber(phoneNumber)
                .email(email)
                .address(address)
                .cardNum(cardNum)
                .user_id(user)
                .build();
    }
}
