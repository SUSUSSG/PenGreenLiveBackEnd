package susussg.pengreenlive.broadcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.WatchTime;
import susussg.pengreenlive.broadcast.service.WatchTimeService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/watch-times")
public class WatchTimeController {

    @Autowired
    private WatchTimeService watchTimeService;

    @Operation(summary = "시청자의 방송 시청시간을 테이블에 저장합니다.", description = "시청자의 방송 시청시간을 테이블에 저장합니다.")
    @PostMapping
    public ResponseEntity<Void> createWatchTime(@RequestBody WatchTime watchTime) {
        watchTimeService.addWatchTime(watchTime);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "평균 시청 시간을 계산합니다.", description = "특정 방송의 평균 시청 시간을 계산합니다.")
    @PostMapping("/calculate/{broadcastSeq}")
    public ResponseEntity<Void> calculateAndDeleteWatchTime(@PathVariable Long broadcastSeq) {
        watchTimeService.calculateAndDeleteWatchTime(broadcastSeq);
        return ResponseEntity.ok().build();
    }
}
