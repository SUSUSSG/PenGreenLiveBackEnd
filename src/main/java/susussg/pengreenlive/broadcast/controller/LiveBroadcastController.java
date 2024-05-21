package susussg.pengreenlive.broadcast.controller;

import io.lettuce.core.dynamic.annotation.Param;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastInfoDTO;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastProductDTO;
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

    @PostMapping("live-notice")
    public ResponseEntity<String> addNotice(@RequestParam long broadcastId, @RequestParam String noticeContent) {
        log.info("공지 등록 컨트롤러 호출");
        liveBroadcastService.addNotice(broadcastId, noticeContent);
        return ResponseEntity.ok().body("공지 등록 완료");
    }
}
