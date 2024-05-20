package susussg.pengreenlive.broadcast.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastInfoDTO;
import susussg.pengreenlive.broadcast.service.LiveBroadcastService;

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
}
