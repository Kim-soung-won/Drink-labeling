package me.firstSpring.config.filters;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 할지 설정
        config.addAllowedOrigin("*"); // 모든 ip에 대해 응답 허용 ("http://localhost:8080", "http://localhost:8081") 등 허용할 출처 설정 가능
        config.addAllowedHeader("*"); // 모든 header에 응답 허용
        config.addAllowedMethod("*"); // 모든 http 메소드 요청 허용 , GET,POST 등 허용할 http 메서드 설정 가능

        source.registerCorsConfiguration("/api/**", config); // api/** 경로에 대해 CORS 설정을 허용한다.
//        /api/** 경로에서만 CORS 검사를 수행하고 다른 경로는 수행하지 않는다.
        return new CorsFilter(source);
    }

}
