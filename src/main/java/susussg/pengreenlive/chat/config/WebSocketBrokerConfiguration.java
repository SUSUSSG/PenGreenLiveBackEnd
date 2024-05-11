package susussg.pengreenlive.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import susussg.pengreenlive.chat.entity.Room;
import susussg.pengreenlive.chat.entity.User;
import susussg.pengreenlive.chat.interceptor.WebsocketBrokerInterceptor;
import susussg.pengreenlive.chat.service.EnteredRoomService;
import susussg.pengreenlive.chat.service.RoomService;
import susussg.pengreenlive.chat.service.UserService;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketBrokerConfiguration implements WebSocketMessageBrokerConfigurer {

    private final UserService userService;
    private final RoomService roomService;
    private final EnteredRoomService enteredRoomService;


    private final WebsocketBrokerInterceptor interceptor;
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/init") //1 최초 websocket을 연결할 때 보내는 endPoint입니다.
            .setAllowedOrigins("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(interceptor); //2 websocket이 연결되거나, sub/pub/send 등 client에서 메시지를 보내게 될 때 interceptor를 통해 핸들링 하게 됩니다.
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub"); //3 client는 /sub/** 의 형태로 topic을 구독하게 됩니다
        registry.setApplicationDestinationPrefixes("/pub"); // 4 메시지를 보낼때는 /pub 형식으로 보내게 됩니다.
    }

    @EventListener(value = ApplicationReadyEvent.class)
    public void addTestData() {

//        User user1 = new User("user1");
//        User user2 = new User("user2");
//        User user3 = new User("user3");
//        User user4 = new User("user4");
//
//        Room room1 = new Room("채팅방1");
//        Room room2 = new Room("채팅방2");
//
//        Long savedUserId1 = userService.save(user1);
//        Long savedUserId2 = userService.save(user2);
//        Long savedUserId3 = userService.save(user3);
//        Long savedUserId4 = userService.save(user4);
//
//
//        Long savedRoomId1 = roomService.save(room1);
//        Long savedRoomId2 = roomService.save(room2);
//
//        enteredRoomService.save(savedUserId1, savedRoomId1);
//        enteredRoomService.save(savedUserId2, savedRoomId1);
//        enteredRoomService.save(savedUserId3, savedRoomId1);
//
//        enteredRoomService.save(savedUserId3, savedRoomId2);
//        enteredRoomService.save(savedUserId4, savedRoomId2);
    }
}
