package susussg.pengreenlive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(new AntPathRequestMatcher("/gpt")) // /api/chat 경로에 대해 CSRF 보호 비활성화
            )
            .authorizeRequests(authorize -> authorize
                .requestMatchers("/gpt").permitAll() // /api/chat 경로에 대해 접근 허용
                .anyRequest().authenticated() // 나머지 경로는 인증 필요
            );
        return http.build();
    }
}