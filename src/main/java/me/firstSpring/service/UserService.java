package me.firstSpring.service;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.User;
import me.firstSpring.dto.User.AddUserRequest;
import me.firstSpring.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


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
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user email"));
    }
    public User findByNickname(String nickname){
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

}
