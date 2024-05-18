package susussg.pengreenlive.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.DefaultCsrfToken;

@Configuration
public class CustomObjectMapperConfig {

    @Bean
    public ObjectMapper customObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(DefaultCsrfToken.class, new CustomCsrfTokenSerializer());
        module.addDeserializer(DefaultCsrfToken.class, new CustomCsrfTokenDeserializer());
        mapper.registerModule(module);
        return mapper;
    }
}