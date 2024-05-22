package susussg.pengreenlive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests.requestMatchers("/**").permitAll();

//                authorizeRequests
//                        .requestMatchers("/login", "/signup", "/find-password", "/reset-password").permitAll();

//                authorizeRequests.requestMatchers("/admin/**")
//                    .hasAuthority(MemberRole.ADMIN.getValue());

//                authorizeRequests.requestMatchers("/member/**", "/**").hasAnyAuthority(
//                        MemberRole.ADMIN.getValue(),
//                        MemberRole.OPERATOR.getValue(),
//                        MemberRole.WAREHOUSE_MANAGER.getValue());
                })
                .headers((headers) -> headers.addHeaderWriter(
                        new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))

                .formLogin((formLogin) -> { formLogin
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .permitAll();
//                .successForwardUrl("/");
                })
                .logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/").invalidateHttpSession(true))
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/");
        };
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations()
                );
    }


}
