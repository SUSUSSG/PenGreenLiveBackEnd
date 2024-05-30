package susussg.pengreenlive.openai.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.login.service.SecurityService;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;
import susussg.pengreenlive.openai.dto.AiBroadcastPromptDTO;
import susussg.pengreenlive.openai.dto.ChatRequestDTO;
import susussg.pengreenlive.openai.dto.ChatResponseDTO;
import susussg.pengreenlive.openai.dto.RecentOrderDTO;
import susussg.pengreenlive.openai.service.OpenAIQueryService;
import susussg.pengreenlive.openai.service.OpenAIService;

@RestController
@RequestMapping("/api/openai")
@Log4j2
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private OpenAIQueryService openAIQueryService;

    @Autowired
    SecurityService securityService;
  
    @Operation(summary = "입장 알림을 위한 메시지를 전송합니다.", description = "챗봇 메시지 전송 및 응답을 처리합니다.")
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
    @Operation(summary = "최근 주문 내역을 조회합니다.", description = "사용자의 최근 주문 내역을 받아와 챗봇에 표시합니다.")
    @GetMapping("/recent-orders")
    public List<RecentOrderDTO> getRecentOrders() {
        String userUUID = securityService.getCurrentUserUuid();
        return openAIQueryService.getRecentOrders(userUUID);
    }
    @Operation(summary = "키워드로 방송을 조회합니다.", description = "사용자가 입력한 키워드가 제목에 포함된 방송을 조회합니다.")
    @GetMapping("/broadcast-keyword")
    public List<ScheduledBroadcastDTO> getBroadcastsByKeyword(@RequestParam String keyword) {
        return openAIQueryService.getBroadcastsByKeyword(keyword);
    }
    @Operation(summary = "프롬프트를 생성합니다.", description = "라이브보드에서 사용될 프롬프트를 생성합니다.")
    @GetMapping("/generate-prompt")
    public String getBroadcastDetailsBySeq(@RequestParam Long broadcastSeq) {
        AiBroadcastPromptDTO aiBroadcastPromptDTO = openAIQueryService.getBroadcastDetailsBySeq(broadcastSeq);

        try {
            return openAIService.generateBroadcastScript(aiBroadcastPromptDTO);
        } catch (Exception e) {
            log.error("Exception occurred while generating broadcast script: " + e.getMessage());
            return "프롬프트 생성 중 오류가 발생했습니다.";
        }
    }
    @Operation(summary = "리뷰 유해성을 확인합니다.", description = "리뷰를 받아 유해성을 판별한 뒤, 유해성 유무를 반환합니다.")
    @PostMapping("/review-check")
    public String checkReviewForHarmfulness(@RequestBody String reviewContent) {
        try {
            log.info("Checking review for harmfulness: " + reviewContent);
            String response = openAIService.checkReviewForHarmfulness(reviewContent);
            log.info("Review check response: " + response);
            return response;
        } catch (Exception e) {
            log.error("Exception occurred while checking review: " + e.getMessage());
            return "error";
        }
    }
}
