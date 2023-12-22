package me.firstSpring.config;

import lombok.RequiredArgsConstructor;
import me.firstSpring.config.filters.CorsConfig;
import me.firstSpring.config.jwt.TokenProvider;
import me.firstSpring.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import me.firstSpring.config.oauth.OAuth2SuccessHandler;
import me.firstSpring.config.oauth.OAuth2UserCustomService;
import me.firstSpring.repository.RefreshTokenRepository;
import me.firstSpring.repository.UserRepository;
import me.firstSpring.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
public class WebOAuthSecurityConfig{
    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final CorsConfig corsFilter;
    private final UserRepository userRepository;
    @Bean
    public WebSecurityCustomizer configure(){ //시큐리티 기능(폼 로그인, 세션기능) 비활성화
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
                .requestMatchers("/img/**","/css/**","/js/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http. //토큰 방식으로 인증하기 때문에 기존에 사용하던 폼 로그인, 세션 비활성화
                csrf().disable()
                .httpBasic().disable()
                .addFilter(corsFilter.corsFilter())
                .formLogin().disable() //폼 로그인 비활성화
                .logout().disable();
        http.
                sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//세션인증 사용 안함

        //헤더를 확인할 커스텀 필터 추가
        //Spring Security 절차 중 UsernamePasswordAuthenticationFilter 앞에 커스텀 필터 배치
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // 토큰 재발급 URL은 인증 없이 접근 가능하도록 설정
        http.authorizeHttpRequests() //Http요청에 대한 보안 구성 시작
                .requestMatchers("/api/token").permitAll() //해당 경로에 대해서는 인증없이 허용
                .requestMatchers("/api/**").authenticated() //해당 경로에 대해서는 모든 경로에 대해 인증 필요
                .requestMatchers("/api/user/**").hasAnyRole("USER","MANAGER","ADMIN")
                .requestMatchers("/api/manager/**").hasAnyRole("MANAGER","ADMIN")
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll(); //나머지 모든 요청 인증 허용

        http.oauth2Login()
                .loginPage("/loginForm")
                .authorizationEndpoint()
                //요청과 관련된 상태 저장
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .successHandler(oAuth2SuccessHandler()) //인증 성공 시 실행할 핸들러
                .userInfoEndpoint()
                .userService(oAuth2UserCustomService);

        http.logout()
                .logoutSuccessUrl("/loginForm");

        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                        new AntPathRequestMatcher("/api/**"));
        return http.build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(tokenProvider,refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                userService
        );
    }
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
