package susussg.pengreenlive.broadcast.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import susussg.pengreenlive.broadcast.dto.Subtitle;
import susussg.pengreenlive.broadcast.service.TranslationService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SubtitleController {

    private final SimpMessagingTemplate template;
    private final TranslationService translationService;

    @MessageMapping("/subtitles/{broadcastId}")
    public void broadcastSubtitle(@DestinationVariable String broadcastId, Subtitle message) {
        log.info("Received subtitle: {}", message);
        try{
            message.setTranslatedText(translationService.translateKoreanToEnglish(message.getText()));
        }catch (Exception e){
            log.info(e.toString());
        }
        template.convertAndSend("/sub/subtitles/" + broadcastId, message);
    }
}

