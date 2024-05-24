package susussg.pengreenlive.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import susussg.pengreenlive.login.service.CustomAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400)
public class SecurityConfig  {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

//    @Autowired
//    private CustomInvalidSessionStrategy customInvalidSessionStrategy;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                    .requestMatchers("/**").permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .sessionFixation(fixation -> fixation.migrateSession())
                    .invalidSessionUrl("/")
//                    .invalidSessionStrategy(customInvalidSessionStrategy)
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredUrl("/session-expired"))

                    .rememberMe(rememberMe -> rememberMe
                    .key("uniqueAndSecret")
                    .tokenValiditySeconds(86400));
        return http.build();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean(authenticationConfiguration));
        filter.setFilterProcessesUrl("/login");
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/static/**", "/scss/**", "/js/**", "/images/**", "/video/**", "/**");
    }

}
