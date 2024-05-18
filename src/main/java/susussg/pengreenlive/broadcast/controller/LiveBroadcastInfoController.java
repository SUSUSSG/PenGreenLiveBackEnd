package susussg.pengreenlive.broadcast.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastInfoDTO;
import susussg.pengreenlive.broadcast.service.LiveBroadcastInfoService;

@RestController
@Log4j2
public class LiveBroadcastInfoController {

    @Autowired
    private final LiveBroadcastInfoService liveBroadcastInfoService;


    public LiveBroadcastInfoController(LiveBroadcastInfoService liveBroadcastInfoService) {
        this.liveBroadcastInfoService = liveBroadcastInfoService;
    }

    @GetMapping("basic-broadcast-info/{broadcastId}")
    public ResponseEntity<LiveBroadcastInfoDTO> fetchBasicBroadcastInfo(@PathVariable long broadcastId){
        LiveBroadcastInfoDTO basicBroadcastInfo = liveBroadcastInfoService.getBasicBroadcastInfo(broadcastId);
        return ResponseEntity.ok().body(basicBroadcastInfo);
    }
}
