package susussg.pengreenlive.chat.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.dto.MessageDto;


@Slf4j
@RestController
@RequiredArgsConstructor
public class BrokerController {

    private final SimpMessagingTemplate template;


    @MessageMapping("/test")
    public void test(SimpMessageHeaderAccessor accessor) {
        log.info("### test method {}", accessor);
    }


    @MessageMapping("/room/{roomId}")
    public void sendMessage(@DestinationVariable(value = "roomId") String roomId, MessageDto message) {
        log.info("# roomId = {}", roomId);
        log.info("# message = {}", message);

        template.convertAndSend("/sub/room/" + roomId, message.getMessage());
    }

    @MessageMapping("/room/{roomId}/entered")
    public void entered(@DestinationVariable(value = "roomId") String roomId, MessageDto message){
        log.info("# roomId = {}", roomId);
        log.info("# message = {}", message);
        final String payload = message.getWriter() + "님이 입장하셨습니다.";
        template.convertAndSend("/sub/room/" + roomId, payload);
    }

    @MessageMapping("/room/{roomId}/leave")
    public void leave(@DestinationVariable(value = "roomId") String roomId, MessageDto messageDto) {
        log.info("# leave method called");
        final String payload = messageDto.getWriter() + "님이 방을 떠났습니다.";
        template.convertAndSend("/sub/room/" + roomId, payload);
    }

}
