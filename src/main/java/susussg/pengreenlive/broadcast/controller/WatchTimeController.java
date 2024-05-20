package susussg.pengreenlive.broadcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.WatchTime;
import susussg.pengreenlive.broadcast.service.WatchTimeService;

@RestController
@RequestMapping("/api/watch-times")
public class WatchTimeController {

    @Autowired
    private WatchTimeService watchTimeService;

    @PostMapping
    public ResponseEntity<Void> createWatchTime(@RequestBody WatchTime watchTime) {
        watchTimeService.addWatchTime(watchTime);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/calculate/{broadcastSeq}")
    public ResponseEntity<Void> calculateAndDeleteWatchTime(@PathVariable Long broadcastSeq) {
        watchTimeService.calculateAndDeleteWatchTime(broadcastSeq);
        return ResponseEntity.ok().build();
    }
}
