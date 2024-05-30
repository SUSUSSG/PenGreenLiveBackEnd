package susussg.pengreenlive.broadcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.BroadcastViewerCount;
import susussg.pengreenlive.broadcast.service.BroadcastViewerCountService;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/broadcast-viewer-count")
public class BroadcastViewerCountController {

    @Autowired
    private BroadcastViewerCountService broadcastViewerCountService;

    @Operation(summary = "방송 시청자 수 통계를 추가합니다.", description = "방송 시청자 수 통계를 추가합니다.")
    @PostMapping("/add")
    public ResponseEntity<Void> addBroadcastViewerCount(@RequestBody BroadcastViewerCount broadcastViewerCount) {
        broadcastViewerCountService.addBroadcastViewerCount(broadcastViewerCount);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "특정 방송의 시청자 수 추이 통계를 조회합니다.", description = "특정 방송 시퀀스 ID에 대한 시청자 수 추이 통계를 조회합니다.")
    @GetMapping("/{broadcastSeq}")
    public ResponseEntity<List<BroadcastViewerCount>> getBroadcastViewerCounts(@PathVariable Long broadcastSeq) {
        List<BroadcastViewerCount> counts = broadcastViewerCountService.getBroadcastViewerCounts(broadcastSeq);
        return ResponseEntity.ok(counts);
    }
}
