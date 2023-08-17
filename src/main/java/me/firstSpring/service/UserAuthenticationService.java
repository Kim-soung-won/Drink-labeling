//package me.firstSpring.service;
//
//import me.firstSpring.config.jwt.TokenProvider;
//import me.firstSpring.domain.User;
//import me.firstSpring.repository.UserRepository;
//import org.springframework.security.core.Authentication;
//
//public class UserAuthenticationService {
//    private final TokenProvider tokenProvider;
//    private final UserService userService;
//    private final UserRepository userRepository;
//
//    public UserAuthenticationService(TokenProvider tokenProvider, UserService userService, UserRepository userRepository) {
//        this.tokenProvider = tokenProvider;
//        this.userService = userService;
//        this.userRepository = userRepository;
//    }
//
//    public Authentication getAuthenticationFromToken(String token) {
//        if (tokenProvider.validToken(token)) {
//            Long userId = tokenProvider.getUserId(token);
//            User user = userRepository.findById(userId).orElse(null);
//
//            if (user != null) {
//                return tokenProvider.getAuthentication(token);
//            }
//        }
//        return null; // Invalid token or user not found
//    }
//
//    // You can add more methods for authentication and user-related operations if needed
//}
