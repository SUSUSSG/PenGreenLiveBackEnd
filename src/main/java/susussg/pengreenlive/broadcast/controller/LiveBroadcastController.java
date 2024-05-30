package susussg.pengreenlive.broadcast.controller;

import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.*;
import susussg.pengreenlive.broadcast.service.LiveBroadcastService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/api")
public class LiveBroadcastController {

    @Autowired
    private final LiveBroadcastService liveBroadcastService;


    public LiveBroadcastController(LiveBroadcastService liveBroadcastService) {
        this.liveBroadcastService = liveBroadcastService;
    }

    @Operation(summary = "기본적인 방송 정보를 조회합니다.", description = "한 방송에 대한 기본적인 정보를 조회합니다.")
    @GetMapping("live-broadcast-info/{broadcastId}")
    public ResponseEntity<LiveBroadcastInfoDTO> fetchBasicBroadcastInfo(@PathVariable("broadcastId") long broadcastId){
        LiveBroadcastInfoDTO basicBroadcastInfo = liveBroadcastService.getBroadcastInfo(broadcastId);
        return ResponseEntity.ok().body(basicBroadcastInfo);
    }

    @Operation(summary = "방송에서 판매하는 상품을 조회합니다.", description = "한 방송에서 판매할 상품들에 대한 정보를 조회합니다.")
    @GetMapping("live-broadcast-product/{broadcastId}")
    public ResponseEntity<List<LiveBroadcastProductDTO>> fetchLiveBroadcastProduct(@PathVariable("broadcastId") long broadcastId){
        log.info("생방송상품 컨트롤러 호출");
        List<LiveBroadcastProductDTO> liveBroadcastProduct = liveBroadcastService.getBroadcastProducts(broadcastId);
        return ResponseEntity.ok().body(liveBroadcastProduct);
    }

    @Operation(summary = "공지를 추가합니다.", description = "방송 중 공지사항을 추가합니다.")
    @PostMapping("live-notice/add")
    public ResponseEntity<NoticeDTO> addNotice(@RequestBody NoticeDTO noticeDTO) {
        NoticeDTO newNotice = liveBroadcastService.addNotice(noticeDTO);
        return ResponseEntity.ok(newNotice);
    }

    @Operation(summary = "공지를 삭제합니다.", description = "방송 중 공지사항을 삭제합니다.")
    @DeleteMapping("live-notice/delete/{noticeId}")
    public ResponseEntity<String> removeNotice(@PathVariable("noticeId") long noticeId) {
        liveBroadcastService.removeNotice(noticeId);
        return ResponseEntity.ok("공지 삭제 성공");
    }

    @Operation(summary = "자주 묻는 질문과 답변을 추가합니다.", description = "방송 중 자주 묻는 질문과 답변을 추가합니다.")
    @PostMapping("live-faq/add")
    public ResponseEntity<FaqDTO> addFaq(@RequestBody FaqDTO faqDTO) {
        FaqDTO newFaq = liveBroadcastService.addFaq(faqDTO);
        return ResponseEntity.ok(newFaq);
    }

    @Operation(summary = "자주 묻는 질문과 답변을 삭제합니다.", description = "방송 중 자주 묻는 질문과 답변을 삭제합니다.")
    @DeleteMapping("live-faq/delete/{faqId}")
    public ResponseEntity<String> removeFaq(@PathVariable("faqId") long faqId) {
        liveBroadcastService.removeFaq(faqId);
        return ResponseEntity.ok("자주 묻는 질문 삭제 성공");
    }

    @Operation(summary = "공지사항과 자주 묻는 질문과 답변을 조회합니다.", description = "방송 중 공지사항과 자주 묻는 질문을 실시간으로 확인할 수 있도록 조회합니다.")
    @GetMapping("live-broadcast-info/{broadcastId}/details")
    public ResponseEntity<Map<String, Object>> getBroadcastDetails(@PathVariable("broadcastId") long broadcastId) {
        Map<String, Object> response = new HashMap<>();
        List<NoticeDTO> notices = liveBroadcastService.getAllNotice(broadcastId);
        List<FaqDTO> faqs = liveBroadcastService.getAllFaq(broadcastId);

        response.put("notices", notices);
        response.put("faqs", faqs);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "주문한 상품에 대한 정보를 조회합니다.", description = "방송 중 주문한 상품에 대한 정보를 실시간으로 확인할 수 있도록 조회합니다.")
    @PostMapping("live-product-stats/{broadcastId}/{productId}")
    public ResponseEntity<LiveProductStatsDTO> fetchLiveProductStats(@PathVariable("broadcastId") long broadcastId, @PathVariable("productId") long productId) {
        LiveProductStatsDTO productStats = liveBroadcastService.getLiveProductStats(broadcastId, productId);
        return ResponseEntity.ok().body(productStats);
    }
}