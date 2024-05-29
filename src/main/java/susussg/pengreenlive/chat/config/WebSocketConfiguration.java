package susussg.pengreenlive.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import susussg.pengreenlive.chat.handler.MyHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //myHandler : 클라이언트가 접속할 때 사용하는 URL 엔드포인트
        registry.addHandler(myHandler(), "/myHandler")
                .setAllowedOriginPatterns("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        //웹소켓 핸들러로 MyHandler 지정
        return new MyHandler();
    }
}