package susussg.pengreenlive.chat.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.chat.dto.MessageDto;

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
        template.convertAndSend("/sub/room/" + roomId, message);  // MessageDto 객체 전체를 전송
    }

    @MessageMapping("/room/{roomId}/entered")
    public void entered(@DestinationVariable(value = "roomId") String roomId, MessageDto message){
        log.info("# roomId = {}", roomId);
        log.info("# message = {}", message);
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        template.convertAndSend("/sub/room/" + roomId, message);  // MessageDto 객체 전체를 전송
    }

    @MessageMapping("/room/{roomId}/leave")
    public void leave(@DestinationVariable(value = "roomId") String roomId, MessageDto messageDto) {
        log.info("# leave method called");
        messageDto.setMessage(messageDto.getWriter() + "님이 방을 떠났습니다.");
        template.convertAndSend("/sub/room/" + roomId, messageDto);  // MessageDto 객체 전체를 전송
    }
    // BrokerController.java에 메시지 타입으로 NOTICE를 처리하는 메소드 추가
    @MessageMapping("/room/{roomId}/notice")
    public void broadcastNotice(@DestinationVariable(value = "roomId") String roomId, MessageDto message) {
        log.info("# notice = {}", message);
        template.convertAndSend("/sub/room/" + roomId + "/notice", message);  // 모든 구독자에게 공지사항 전송
    }

}
