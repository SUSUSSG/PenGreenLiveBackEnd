package susussg.pengreenlive.openai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.openai.dto.ChatRequestDTO;
import susussg.pengreenlive.openai.dto.ChatResponseDTO;
import susussg.pengreenlive.openai.service.OpenAIService;

@RestController
@RequestMapping("/openai")
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/message")
    public ChatResponseDTO getChatbotResponse(@RequestBody ChatRequestDTO chatRequestDTO) {
        try {
            String originalResponse = openAIService.getChatbotResponse(chatRequestDTO.getMessage());
            String parsedResponse = openAIService.parseResponse(originalResponse);
            return new ChatResponseDTO(parsedResponse);
        } catch (Exception e) {
            return new ChatResponseDTO(null);
        }
    }
}