package susussg.pengreenlive.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("PengreenLive 친환경 라이브 커머스")
            .version("v1")
            .description("이 API 문서는 친환경 라이브 커머스 서비스 PengreenLive 서비스에 대한 REST API에 대한 설명서입니다.")
            .contact(new Contact()
                .name("홍진욱, 장서윤, 구민석, 손혜지, 김소진")
                .url("https://github.com/SUSUSSG")));
  }
}

