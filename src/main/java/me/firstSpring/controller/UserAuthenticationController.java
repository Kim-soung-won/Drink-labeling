package me.firstSpring.controller;

import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.service.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

public class UserAuthenticationController {
    private final TokenProvider tokenProvider;
    private final UserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(TokenProvider tokenProvider, UserAuthenticationService userAuthenticationService) {
        this.tokenProvider = tokenProvider;
        this.userAuthenticationService = userAuthenticationService;
    }

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        Authentication authentication = userAuthenticationService.getAuthenticationFromToken(token);

        if (authentication != null) {
            String username = authentication.getName();
            return ResponseEntity.ok("Hello, " + username + "!");
        } else {
            return ResponseEntity.badRequest().body("Invalid token or user not found");
        }
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
