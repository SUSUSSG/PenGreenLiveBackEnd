package susussg.pengreenlive.broadcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.BroadcastViewerCount;
import susussg.pengreenlive.broadcast.service.BroadcastViewerCountService;

import java.util.List;

@RestController
@RequestMapping("/api/broadcast-viewer-count")
public class BroadcastViewerCountController {

    @Autowired
    private BroadcastViewerCountService broadcastViewerCountService;

    @PostMapping("/add")
    public ResponseEntity<Void> addBroadcastViewerCount(@RequestBody BroadcastViewerCount broadcastViewerCount) {
        broadcastViewerCountService.addBroadcastViewerCount(broadcastViewerCount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{broadcastSeq}")
    public ResponseEntity<List<BroadcastViewerCount>> getBroadcastViewerCounts(@PathVariable Long broadcastSeq) {
        List<BroadcastViewerCount> counts = broadcastViewerCountService.getBroadcastViewerCounts(broadcastSeq);
        return ResponseEntity.ok(counts);
    }
}