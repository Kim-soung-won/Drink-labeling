package me.firstSpring.dto.UserDetailBasic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.Drink;
import me.firstSpring.domain.User;
import me.firstSpring.domain.UserDetailBasic;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserDetailRequest {
    private String name;
    private String birth;
    private String sex;
    private String phoneNumber;
    private String email;
    private String address;
    private String cardNum;
    private User user_id;
    public UpdateUserDetailRequest(UserDetailBasic user){
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.address=user.getAddress();
        this.cardNum=user.getCardNum();
    }
}
