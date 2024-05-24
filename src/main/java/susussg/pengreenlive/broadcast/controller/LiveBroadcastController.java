package susussg.pengreenlive.broadcast.controller;

import io.lettuce.core.dynamic.annotation.Param;
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

    @GetMapping("live-broadcast-info/{broadcastId}")
    public ResponseEntity<LiveBroadcastInfoDTO> fetchBasicBroadcastInfo(@PathVariable("broadcastId") long broadcastId){
        LiveBroadcastInfoDTO basicBroadcastInfo = liveBroadcastService.getBroadcastInfo(broadcastId);
        return ResponseEntity.ok().body(basicBroadcastInfo);
    }

    @GetMapping("live-broadcast-product/{broadcastId}")
    public ResponseEntity<List<LiveBroadcastProductDTO>> fetchLiveBroadcastProduct(@PathVariable("broadcastId") long broadcastId){
        log.info("생방송상품 컨트롤러 호출");
        List<LiveBroadcastProductDTO> liveBroadcastProduct = liveBroadcastService.getBroadcastProducts(broadcastId);
        return ResponseEntity.ok().body(liveBroadcastProduct);
    }

    @PostMapping("live-notice/add")
    public ResponseEntity<NoticeDTO> addNotice(@RequestBody NoticeDTO noticeDTO) {
        NoticeDTO newNotice = liveBroadcastService.addNotice(noticeDTO);
        return ResponseEntity.ok(newNotice);
    }

    @DeleteMapping("live-notice/delete/{noticeId}")
    public ResponseEntity<String> removeNotice(@PathVariable("noticeId") long noticeId) {
        liveBroadcastService.removeNotice(noticeId);
        return ResponseEntity.ok("공지 삭제 성공");
    }

    @PostMapping("live-faq/add")
    public ResponseEntity<FaqDTO> addFaq(@RequestBody FaqDTO faqDTO) {
        FaqDTO newFaq = liveBroadcastService.addFaq(faqDTO);
        return ResponseEntity.ok(newFaq);
    }

    @DeleteMapping("live-faq/delete/{faqId}")
    public ResponseEntity<String> removeFaq(@PathVariable("faqId") long faqId) {
        liveBroadcastService.removeFaq(faqId);
        return ResponseEntity.ok("자주 묻는 질문 삭제 성공");
    }

    @GetMapping("live-broadcast-info/{broadcastId}/details")
    public ResponseEntity<Map<String, Object>> getBroadcastDetails(@PathVariable("broadcastId") long broadcastId) {
        Map<String, Object> response = new HashMap<>();
        List<NoticeDTO> notices = liveBroadcastService.getAllNotice(broadcastId);
        List<FaqDTO> faqs = liveBroadcastService.getAllFaq(broadcastId);

        response.put("notices", notices);
        response.put("faqs", faqs);

        return ResponseEntity.ok(response);
    }

    @PostMapping("live-product-stats/{broadcastId}/{productId}")
    public ResponseEntity<LiveProductStatsDTO> fetchLiveProductStats(@PathVariable("broadcastId") long broadcastId, @PathVariable("productId") long productId) {
        LiveProductStatsDTO productStats = liveBroadcastService.getLiveProductStats(broadcastId, productId);
        return ResponseEntity.ok().body(productStats);
    }
}