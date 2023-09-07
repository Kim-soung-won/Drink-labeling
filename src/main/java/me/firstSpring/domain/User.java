package me.firstSpring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails{
    //UserDetail : Spring Security에서 사용하는 사용자 정보를 나타내는 핵심 인터페이스
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name ="age")
    private Long age;

    @Column(name = "weight")
    private Long weight;

    @Column(name = "tall")
    private Long tall;


    @Builder
    public User(String email, String password, String nickname, Long age, Long weight, Long tall){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.weight = weight;
        this.tall = tall;
    }

    @Override // 권한부여 메소드 오버라이딩
    //GrantedAuthority = 사용자가 가진 권한을 반환함
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    //사용자 이름 변경
    public User update(String nickname){
        this.nickname = nickname;
        return this;
    }

    public void update_data(Long age, Long weight, Long tall) {
        this.age = age;
        this.weight = weight;
        this.tall = tall;
    }

}
