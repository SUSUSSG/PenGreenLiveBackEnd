package susussg.pengreenlive.chat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) throws JsonProcessingException {
        if (ChatMessage.MessageType.ENTER.equals(message.getType()))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");

        String jsonMessage = objectMapper.writeValueAsString(message);
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), jsonMessage);
    }
}
