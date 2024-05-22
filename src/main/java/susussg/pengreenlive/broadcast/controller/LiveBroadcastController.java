package susussg.pengreenlive.broadcast.controller;

import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.FaqDTO;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastInfoDTO;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastProductDTO;
import susussg.pengreenlive.broadcast.dto.NoticeDTO;
import susussg.pengreenlive.broadcast.service.LiveBroadcastService;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
public class LiveBroadcastController {

    @Autowired
    private final LiveBroadcastService liveBroadcastService;


    public LiveBroadcastController(LiveBroadcastService liveBroadcastService) {
        this.liveBroadcastService = liveBroadcastService;
    }

    @GetMapping("live-broadcast-info/{broadcastId}")
    public ResponseEntity<LiveBroadcastInfoDTO> fetchBasicBroadcastInfo(@PathVariable long broadcastId){
        LiveBroadcastInfoDTO basicBroadcastInfo = liveBroadcastService.getBroadcastInfo(broadcastId);
        return ResponseEntity.ok().body(basicBroadcastInfo);
    }

    @GetMapping("live-broadcast-product/{broadcastId}")
    public ResponseEntity<List<LiveBroadcastProductDTO>> fetchLiveBroadcastProduct(@PathVariable long broadcastId){
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
    public ResponseEntity<String> removeNotice(@PathVariable long noticeId) {
        liveBroadcastService.removeNotice(noticeId);
        return ResponseEntity.ok("공지 삭제 성공");
    }

    @PostMapping("live-faq/add")
    public ResponseEntity<FaqDTO> addFaq(@RequestBody FaqDTO faqDTO) {
        FaqDTO newFaq = liveBroadcastService.addFaq(faqDTO);
        return ResponseEntity.ok(newFaq);
    }
}
