package susussg.pengreenlive.gpt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.gpt.dto.ChatRequest;
import susussg.pengreenlive.gpt.dto.ChatResponse;
import susussg.pengreenlive.gpt.service.OpenAIService;

@RestController
@RequestMapping("/gpt")
public class OpenAIController {

    private final OpenAIService openAIService;

    @Autowired
    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping
    public ChatResponse chat(@RequestBody ChatRequest chatRequest) {
        try {
            String response = openAIService.getChatbotResponse(chatRequest.getMessage());
            return new ChatResponse(response);
        } catch (Exception e) {
            return new ChatResponse("Error: " + e.getMessage());
        }
    }
}