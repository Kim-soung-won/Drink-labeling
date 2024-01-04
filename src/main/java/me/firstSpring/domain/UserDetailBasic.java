package me.firstSpring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_details_basic")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "birth")
    private String birth;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "card_num")
    private String cardNum;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    //
    @OneToMany(mappedBy = "userId")
    private List<Orders> orders = new ArrayList<>(); // ArrayList로 초기화하면 ADD할때 null포인터가 안나타난다. 관례로 사용한다.

    // 생성자를 통한 초기화 코드
    @Builder
    public UserDetailBasic(String name, String birth, String sex, String phoneNumber, String email, String address, String cardNum, User user_id) {
        this.name = name;
        this.birth = birth;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.cardNum = cardNum;
        this.user = user_id;
    }
    public void update(String phoneNumber, String email, String address, String cardNum) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.cardNum = cardNum;
    }
}
