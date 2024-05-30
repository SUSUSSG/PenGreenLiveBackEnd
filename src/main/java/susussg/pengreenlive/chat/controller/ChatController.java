package susussg.pengreenlive.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import susussg.pengreenlive.chat.model.ChatMessage;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ObjectMapper objectMapper;
    @Operation(summary = "입장 알림을 위한 메시지를 전송합니다.", description = "입력받는 메시지의 타입에 따라 채팅 메시지를 모든 구독자에게 전송합니다.")
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) throws JsonProcessingException {
        if (ChatMessage.MessageType.ENTER.equals(message.getType()))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");

        String jsonMessage = objectMapper.writeValueAsString(message);
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), jsonMessage);
    }
}
