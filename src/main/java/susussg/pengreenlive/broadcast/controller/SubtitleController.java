package susussg.pengreenlive.broadcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import susussg.pengreenlive.broadcast.dto.Subtitle;

@RestController
@Slf4j
public class SubtitleController {

    private final SimpMessagingTemplate template;

    @Autowired
    public SubtitleController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/subtitles/{broadcastId}")
    public void broadcastSubtitle(@DestinationVariable String broadcastId, Subtitle message) {
        log.info("Received subtitle: {}", message);
        template.convertAndSend("/sub/subtitles/" + broadcastId, message);
    }
}

