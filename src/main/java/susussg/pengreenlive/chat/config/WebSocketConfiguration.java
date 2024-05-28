package susussg.pengreenlive.chat.config;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import susussg.pengreenlive.chat.handler.MyHandler;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //myHandler : 클라이언트가 접속할 때 사용하는 URL 엔드포인트
        registry.addHandler(myHandler(), "/myHandler").setAllowedOriginPatterns("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        //웹소켓 핸들러로 MyHandler 지정
        return new MyHandler(redisTemplate);
    }
}