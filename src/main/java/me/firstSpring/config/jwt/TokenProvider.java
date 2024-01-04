package me.firstSpring.config.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import me.firstSpring.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;
    public String generateToken(User user, Duration expiredAt){ //유저, 기간
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()),user);
        //to.Millis() = 해당 시간을 밀리초 단위로 반환함
    }
    //JWT 토큰 생성 메서드
    private String makeToken(Date expiry, User user){ //expiry = 만료시간 , User = 유저 정보
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 typ(타입) : JWT
                .setIssuer(jwtProperties.getIssuer()) //내용 iss(발급자) : ajufresh@gmail.com(Properties 파일에서 설정한 값)
                .setExpiration(now) // iat(발급일시) :현재시간
                .setExpiration(expiry) // exp(만료일시) : expiry 멤버 변수값
                .setSubject(user.getEmail()) //sub(토큰 제목) : 유저의 이메일
                .claim("id",user.getId()) // id : 유저의 ID
                .claim("email",user.getEmail())
                .claim("role", user.getRole())
                .signWith(SignatureAlgorithm.HS256,jwtProperties.getSecretKey())
                //서명 : 비밀값과 함깨 해시값을 HS256방식으로 암호화
                .compact();
    }
    //JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 토큰 복호화 진행
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) { //복호화 과정에서 에러가 발생하면 유효하지 않은 토큰
            return false;
        }
    }
    //토큰을 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(getUserRole(token)));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.
                User(claims.getSubject(),"",authorities),token,authorities);
    }
    //토큰 기반으로 유저 아이디를 가져오는 메서드
    public Long getUserId(String token){
        Claims claims = getClaims(token);
        return claims.get("id",Long.class);
    }
    public String getUserRole(String token){
        Claims claims = getClaims(token);
        return claims.get("role",String.class);
    }

    public String extractUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }
    private Claims getClaims(String token){
        return Jwts.parser() //클레임 조회
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
