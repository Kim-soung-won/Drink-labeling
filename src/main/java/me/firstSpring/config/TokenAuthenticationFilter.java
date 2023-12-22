package me.firstSpring.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.firstSpring.config.jwt.JwtProperties;
import me.firstSpring.config.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain)  throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        System.out.println("request:"+request.getMethod());
        if (authorizationHeader != null) {
            // 토큰의 헤더 제거
            String jwtToken = request.getHeader(HEADER_AUTHORIZATION).replace("Bearer ","");
            System.out.println("jwtToken : "+jwtToken);
            //토큰 값 분해
            Claims claims = Jwts.parser().setSigningKey("study-springboot").parseClaimsJws(jwtToken).getBody();
            System.out.println("claims : "+claims);
            System.out.println("sub : "+claims.getSubject());
            System.out.println("email : "+claims.get("email"));
            System.out.println("role : "+claims.get("role"));
        }
        System.out.println("Header:"+authorizationHeader);
        String token = getAccessToken(authorizationHeader);

        if (tokenProvider.validToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            System.out.println("authentication : "+authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}