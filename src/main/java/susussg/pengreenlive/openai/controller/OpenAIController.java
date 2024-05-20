package susussg.pengreenlive.openai.controller;

import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;
import susussg.pengreenlive.openai.dto.ChatRequestDTO;
import susussg.pengreenlive.openai.dto.ChatResponseDTO;
import susussg.pengreenlive.openai.dto.RecentOrderDTO;
import susussg.pengreenlive.openai.service.OpenAIQueryService;
import susussg.pengreenlive.openai.service.OpenAIService;

@RestController
@RequestMapping("/openai")
@Log4j2
public class OpenAIController {


    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private OpenAIQueryService openAIQueryService;

    private final String userUuid = "f23a72e0-1347-11ef-b085-f220affc9a21"; //TODO 세션값으로 바꿔야함
    @PostMapping("/message")
    public ChatResponseDTO getChatbotResponse(@RequestBody ChatRequestDTO chatRequestDTO) {
        try {
            String response = openAIService.getChatbotResponse(chatRequestDTO.getMessage());
            log.info("response: " + response);
            return new ChatResponseDTO(response);
        } catch (Exception e) {
            log.info("Exception occurred: " + e.getMessage());
            return new ChatResponseDTO(null);
        }
    }

    @GetMapping("/recent-orders")
    public List<RecentOrderDTO> getRecentOrders() {
        return openAIQueryService.getRecentOrders(userUuid);
    }

    @GetMapping("/broadcast-keyword")
    public List<ScheduledBroadcastDTO> getBroadcastsByKeyword(@RequestParam String keyword) {
        return openAIQueryService.getBroadcastsByKeyword(keyword);
    }
}
