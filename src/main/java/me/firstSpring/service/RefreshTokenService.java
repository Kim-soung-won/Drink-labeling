package me.firstSpring.service;

import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.ReFreshToken;
import me.firstSpring.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

//    리프레시 토큰으로 토큰 객체를 검색해서 전달
    public ReFreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new IllegalArgumentException("Unexpected token"));
    }
}
