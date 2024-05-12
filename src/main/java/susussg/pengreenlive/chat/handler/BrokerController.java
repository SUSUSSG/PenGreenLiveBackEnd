package susussg.pengreenlive.chat.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.chat.dto.MessageDto;
import susussg.pengreenlive.util.DTO.BanwordValidationResultDTO;
import susussg.pengreenlive.util.Service.BanwordService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BrokerController {

    private final SimpMessagingTemplate template;

    @Autowired
    private BanwordService banwordService;
    @MessageMapping("/test")
    public void test(SimpMessageHeaderAccessor accessor) {
        log.info("### test method {}", accessor);
    }

    @MessageMapping("/room/{roomId}")
    public void sendMessage(@DestinationVariable(value = "roomId") String roomId, MessageDto message) {
        log.info("# roomId = {}", roomId);
        log.info("# message = {}", message);

        BanwordValidationResultDTO validationResult = banwordService.validate(message.getMessage());
        log.info("메시지 필터링 = {}", validationResult);

        // Banword가 발견되면 메시지 대체
        if (!validationResult.getBanwordsFound().isEmpty()) {
            String filteredMessage = message.getMessage().replaceAll(validationResult.getOriginalText(), "비속어가 포함된 채팅입니다.");
            message.setMessage(filteredMessage);
        }

        template.convertAndSend("/sub/room/" + roomId, message);
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
