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
import susussg.pengreenlive.user.Service.UserService;
import susussg.pengreenlive.user.Service.UserServiceImpl;
import susussg.pengreenlive.util.DTO.BanwordValidationResultDTO;
import susussg.pengreenlive.util.Service.BanwordService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BrokerController {

    private final SimpMessagingTemplate template;
    private final UserService userService;
    private final BanwordService banwordService;


    @MessageMapping("/test")
    public void test(SimpMessageHeaderAccessor accessor) {
        log.info("### test method {}", accessor);
    }

    @MessageMapping("/room/{roomId}")
    public void sendMessage(@DestinationVariable(value = "roomId") String roomId,
        MessageDto message, SimpMessageHeaderAccessor accessor) {
        // 세션에 하드코딩된 값 추가
        accessor.getSessionAttributes().put("userUUID", "a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6");

        String userUUID = (String) accessor.getSessionAttributes().get("userUUID");
        String userNm = (String) accessor.getSessionAttributes().get("userNm");

        if (userNm == null) {
            userNm = userService.getUserNmByUUID(userUUID);
            accessor.getSessionAttributes().put("userNm", userNm);
        }

        message.setWriter(userNm);
        log.info("# roomId = {}", roomId);
        log.info("# message = {}", message);

        BanwordValidationResultDTO validationResult = banwordService.validate(message.getMessage());
        log.info("메시지 필터링 = {}", validationResult);

        // Banword가 발견되면 메시지 대체
        if (!validationResult.getBanwordsFound().isEmpty()) {
            String filteredMessage = message.getMessage()
                .replaceAll(validationResult.getOriginalText(), "비속어가 포함된 채팅입니다.");
            message.setMessage(filteredMessage);
        }

        template.convertAndSend("/sub/room/" + roomId, message);
    }

    @MessageMapping("/room/{roomId}/entered")
    public void entered(@DestinationVariable(value = "roomId") String roomId, MessageDto message) {
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
    public void broadcastNotice(@DestinationVariable(value = "roomId") String roomId,
        MessageDto message) {
        log.info("# notice = {}", message);
        template.convertAndSend("/sub/room/" + roomId + "/notice", message);  // 모든 구독자에게 공지사항 전송
    }

}
