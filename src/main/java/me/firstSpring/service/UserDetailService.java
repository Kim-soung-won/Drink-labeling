package me.firstSpring.service;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.User;
import me.firstSpring.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
// Spring Security에서 사용자 정보를 가져오는 인터페이스
public class UserDetailService implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email){
        return userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException((email)));
    }
}
