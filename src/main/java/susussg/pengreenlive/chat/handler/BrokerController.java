package susussg.pengreenlive.chat.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import susussg.pengreenlive.chat.dto.MessageDto;
import susussg.pengreenlive.login.service.SecurityService;
import susussg.pengreenlive.user.service.UserService;
import susussg.pengreenlive.user.service.UserServiceImpl;
import susussg.pengreenlive.util.DTO.BanwordValidationResultDTO;
import susussg.pengreenlive.util.Service.BanwordService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BrokerController {

    private final SimpMessagingTemplate template;
    private final UserService userService;
    private final BanwordService banwordService;
    private final SecurityService securityService;
    private final RedisTemplate<String, Object> redisTemplate;


    @MessageMapping("/test")
    public void test(SimpMessageHeaderAccessor accessor) {
        log.info("### test method {}", accessor);
    }

    // 사용 중인 숫자를 관리하는 Set
    private final Set<Integer> usedNumbers = new HashSet<>();

    @MessageMapping("/room/{roomId}")
    public void sendMessage(@DestinationVariable(value = "roomId") String roomId,
        MessageDto message, SimpMessageHeaderAccessor accessor) {

        String userUUID = securityService.getCurrentUserUuid();
        String userNm = securityService.getCurrentUserNm();
        Long vendorSeq = securityService.getCurrentVendorSeq();
        String sessionId = accessor.getSessionId();


        if (userUUID == null && vendorSeq == null) {
            userNm = (String) redisTemplate.opsForHash().get(sessionId, "userNm");

            if (userNm == null) {
                // 사전에 설정한 닉네임 리스트
                List<String> nicknameList = Arrays.asList("행복한펭귄", "심심한펭귄", "슬픈펭귄", "기쁜펭귄",
                        "노래하는펭귄", "배부른펭귄", "물고기먹는펭귄", "슈슈슉펭귄", "잠자는펭귄", "헤엄치는펭귄", "황제펭귄", "날개짓하는펭귄",
                        "뛰어다니는펭귄", "눈사람만드는펭귄", "물고기잡는펭귄", "빙하위를걷는펭귄", "무지개색펭귄", "눈싸움하는펭귄", "코딩하는펭귄",
                        "졸린펭귄", "잠오는펭귄", "산책하는펭귄", "코노가는펭귄", "한강펭귄", "게으른펭귄", "물고기사냥하는펭귄", "햇살쨍쨍펭귄",
                        "하늘나는펭귄", "놀고있는펭귄", "나펭귄아니다", "갯벌사는펭귄", "커피마시는펭귄", "매킨토시펭귄");

                // 랜덤으로 닉네임 선택
                Random random = new Random();
                int randomIndex = random.nextInt(nicknameList.size());
                String randomNickname = nicknameList.get(randomIndex);

                // 사용 중이지 않은 숫자 생성
                int randomNumber;
                do {
                    randomNumber = random.nextInt(1000);
                } while (usedNumbers.contains(randomNumber));

                // 사용한 숫자 Set에 추가
                usedNumbers.add(randomNumber);

                // 닉네임과 숫자 조합하여 유저 이름 생성
                userNm = randomNickname + "_" + String.format("%03d", randomNumber);
                redisTemplate.opsForHash().put(sessionId, "userNm", userNm);
            }
        }
        if (vendorSeq != null) {
            String channelNm = userService.getChannelNmByVendorSeq(vendorSeq);
            redisTemplate.opsForHash().put(sessionId, "channelNm", userNm);
            message.setWriter(channelNm);
        } else {
            message.setWriter(userNm);
            log.info("내 이름이 뭐게 {}", userNm);
        }

        log.info("# roomId = {}", roomId);
        log.info("# message = {}", message);
        banwordService.updateIndividualBanwordTrie(Integer.parseInt(roomId));

        BanwordValidationResultDTO validationResult = banwordService.validate(message.getMessage());
        log.info("메시지 필터링 = {}", validationResult);

        // Banword가 발견되면 메시지 대체
        if (!validationResult.getBanwordsFound().isEmpty()) {
            String filteredMessage = "비속어가 포함된 채팅입니다.😕";
            message.setMessage(filteredMessage);
        }

        template.convertAndSend("/sub/room/" + roomId, message);
    }


    @MessageMapping("/room/{roomId}/entered")
    public void entered(@DestinationVariable(value = "roomId") String roomId, MessageDto message) {
        log.info("# roomId = {}", roomId);
        log.info("# message = {}", message);
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");

//        template.convertAndSend("/sub/room/" + roomId, message);  // MessageDto 객체 전체를 전송
    }

    @MessageMapping("/room/{roomId}/leave")
    public void leave(@DestinationVariable(value = "roomId") String roomId, MessageDto messageDto) {
        log.info("# leave method called");
        messageDto.setMessage(messageDto.getWriter() + "님이 방을 떠났습니다.");
//        template.convertAndSend("/sub/room/" + roomId, messageDto);  // MessageDto 객체 전체를 전송
    }

    // BrokerController.java에 메시지 타입으로 NOTICE를 처리하는 메소드 추가
    @MessageMapping("/room/{roomId}/notice")
    public void broadcastNotice(@DestinationVariable(value = "roomId") String roomId,
        MessageDto message) {
        log.info("# notice = {}", message);
        template.convertAndSend("/sub/room/" + roomId + "/notice", message);  // 모든 구독자에게 공지사항 전송
    }

    //세션 종료 시 usedNumbers에서 제거
//    @EventListener
//    public void handleSessionDisconnect(SessionDisconnectEvent event) {
//        String userNm = (String) event.getSession().getAttributes().get("userNm");
//        if (userNm != null) {
//            String[] parts = userNm.split("_");
//            if (parts.length == 2) {
//                int number = Integer.parseInt(parts[1]);
//                usedNumbers.remove(number);
//            }
//        }
//    }

}
