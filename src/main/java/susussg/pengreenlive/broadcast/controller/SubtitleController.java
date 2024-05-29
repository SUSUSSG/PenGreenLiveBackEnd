package susussg.pengreenlive.broadcast.controller;

import lombok.RequiredArgsConstructor;
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
    public void broadcastSubtitle(@DestinationVariable String broadcastId, Subtitle subtitle) {
        log.info("Received subtitle: {}", subtitle);
        try{
            String eng = translationService.translateKoreanToEnglish(subtitle.getText());
            log.info(eng);
            subtitle.setTranslatedText(eng);
        }catch (Exception e){
            log.info(e.toString());
        }
        log.info("Parsing subtitle: {}", subtitle);
        template.convertAndSend("/sub/subtitles/" + broadcastId, subtitle);
    }
}

