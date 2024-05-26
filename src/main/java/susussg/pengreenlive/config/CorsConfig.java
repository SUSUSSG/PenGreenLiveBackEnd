package susussg.pengreenlive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 URL 패턴
//            .allowedOriginPatterns("*")  // Vue.js 서버의 URL과 포트
            .allowedOrigins("http://localhost:5173")  // 허용할 오리진
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")  // 모든 HTTP 메소드 허용
            .allowedHeaders("*")  // 모든 헤더 허용

            .allowCredentials(true)  // 쿠키 및 인증 정보 포함 허용
            .maxAge(3600);  // 3600초 동안 pre-flight(사전 전송 검사) 결과 캐시
      }
    };
  }
}

