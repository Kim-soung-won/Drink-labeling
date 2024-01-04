package me.firstSpring.dto.UserDetailBasic;

import jakarta.validation.constraints.NotEmpty;

public class SignupUserDetailRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String birth;
    @NotEmpty
    private String sex;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private String email;
    @NotEmpty
    private String address;
    private String cardNum;
    @NotEmpty
    private Long user_id;
}
