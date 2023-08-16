package me.firstSpring.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.domain.Article;
import me.firstSpring.domain.User;
import me.firstSpring.dto.AddArticleRequest;
import me.firstSpring.dto.AddUserRequest;
import me.firstSpring.dto.UpdateUserRequest;
import me.firstSpring.repository.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Long save(AddUserRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build()).getId();
    }

    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new IllegalArgumentException("Unexpected user"));
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("Unexpected user"));
    }
    @Transactional //트랜젝션
    public User update(long id, UpdateUserRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        user.update_data(request.getNickName());

        return user;
    }


}
