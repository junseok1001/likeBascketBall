package com.sourjelly.likebasketball.common.BcryptEncoder;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 2. HTTP 보안 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 초기 개발 시 편리함을 위해 CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        // 콜백 주소는 누구나 접근 가능해야 합니다.
                        .requestMatchers("/", "/user/kakao/callback", "/user/**").permitAll()
                        .anyRequest().permitAll() // 모든인증 허용
                        //.anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
                )
                .formLogin(login -> login.disable()) // 기본 로그인 페이지 비활성화 (필요시 설정)
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
