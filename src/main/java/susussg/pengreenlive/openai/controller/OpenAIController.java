package susussg.pengreenlive.openai.controller;

import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class OpenAIController {


    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/message")
    public ChatResponseDTO getChatbotResponse(@RequestBody ChatRequestDTO chatRequestDTO) {
        try {
            String originalResponse = openAIService.getChatbotResponse(chatRequestDTO.getMessage());
            log.info("Original Response: " + originalResponse);
            String parsedResponse = openAIService.parseResponse(originalResponse);
            log.info("Parsed Response: " + parsedResponse);
            return new ChatResponseDTO(parsedResponse);
        } catch (Exception e) {
            log.info("Exception occurred: " + e.getMessage());
            return new ChatResponseDTO(null);
        }
    }
}
