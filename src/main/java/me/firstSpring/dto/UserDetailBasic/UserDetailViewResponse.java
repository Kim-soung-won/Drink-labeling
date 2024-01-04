package me.firstSpring.dto.UserDetailBasic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.firstSpring.domain.User;
import me.firstSpring.domain.UserDetailBasic;

@NoArgsConstructor
@Getter
public class UserDetailViewResponse {

    private Long id;
    private String email;
    private String name;
    private String address;
    private String phoneNumber;
    private String sex;
    private String cardNum;
    private String birth;

    public UserDetailViewResponse(UserDetailBasic user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.birth = user.getBirth();
        this.name = user.getName();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
        this.sex = user.getSex();
        this.cardNum = user.getCardNum();
    }
}
