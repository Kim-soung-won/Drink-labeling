package me.firstSpring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_details_basic")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth", nullable = false)
    private String birth;

    @Column(name = "sex", nullable = false)
    private int sex;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "card_num", unique = true)
    private String cardNum;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    // 생성자를 통한 초기화 코드
    @Builder
    public UserDetailBasic(String name, String birth, int sex, String phoneNumber, String email, String address, String cardNum, User user) {
        this.name = name;
        this.birth = birth;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.cardNum = cardNum;
        this.user = user;
    }
}
