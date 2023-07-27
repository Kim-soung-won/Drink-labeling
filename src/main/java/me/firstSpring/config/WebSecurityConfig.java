//package me.firstSpring.config;
//
//import lombok.RequiredArgsConstructor;
//import me.firstSpring.service.UserDetailService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//@RequiredArgsConstructor
//@Configuration
////보안 설정
//public class WebSecurityConfig {
//    private final UserDetailService userService;
//
//    @Bean //스프링 시큐리티의 모든 기능을 사용하지 않게 설정하는 코드
//    //일반적인 정적 리소스에 대해 설정한다.
//    public WebSecurityCustomizer configure(){
//        return (web) -> web.ignoring()
//                .requestMatchers(toH2Console())
//                .requestMatchers("/static/**");
//    }
//
//    @Bean //특정 HTTP 요성에 대해 웹 기반 보안 구성
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//        return http
//                .authorizeHttpRequests() //특정 경로에 대한 액세스 설정 and()이전이 하위 설정
//                .requestMatchers("/login", "/signup", "/user")//특정 요청과 일치하는 url에 대한 액세스 설정
//                .permitAll() //누구나 접근가능 -> login, signup, user로 요청이 인가없이 접근 가능
//                .anyRequest()// 위에서 설정한 url 이외의 요청에 대해 설정
//                .authenticated()//별도의 인가는 필요하지 않지만 인증이 성공된 상태여야 접근 가능
//                .and()
//                .formLogin()// 폼 기반 로그인 설정
//                .loginPage("/login")//로그인 페이지 경로설정
//                .defaultSuccessUrl("/article") //로그인 완료시 이동할 경로 설정
//                .and()
//                .logout()//로그아웃 설정
//                .logoutSuccessUrl("/login") //로그아웃이 완료되면 이동할 경로
//                .invalidateHttpSession(true)
//                .and()
//                .csrf().disable() //CSRF 설정 비활성화 보안설정을 비활성화 하는것 실습을 편하게 하기위해서
//                .build();
//    }
//
//    @Bean //인증 관리자 관련 설정
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
//                                                       UserDetailService userDetailService) throws Exception{
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userService)
//                //사용자 정보를 가져올 서비스를 설정한다. 이때 클래스는 반드시 UserDetailsService를 상속받은 상태여야 한다.
//                .passwordEncoder(bCryptPasswordEncoder) //패스워드를 암호화하기위한 Encoder를 설정한다.
//                .and()
//                .build();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
