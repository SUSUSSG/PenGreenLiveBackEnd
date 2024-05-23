package susussg.pengreenlive.main.Controller;

import java.time.LocalDate;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.config.aop.TimeTrace;
import susussg.pengreenlive.main.DTO.LiveChanceCarouselDTO;
import susussg.pengreenlive.main.DTO.MainCarouselDTO;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;
import susussg.pengreenlive.main.DTO.SubscribedChannelDTO;
import susussg.pengreenlive.main.Service.MainService;

@Log4j2
@RestController
public class MainController {

    @Autowired
    MainService mainService;

    // 세션에 하드코딩된 값 추가
    // 테스트 시 주석처리 되지 않은 역할로 채팅 진입
    public String userUUID = "f23a72e0-1347-11ef-b085-f220affc9a21";
    @TimeTrace
    @GetMapping("/main-carousels")
    public List<MainCarouselDTO> getMainCarousels(){
        log.info("call getMainCarousels");
        return mainService.getMainCarousels();
    }
    @TimeTrace
    @GetMapping("/schedule")
    public ResponseEntity<List<ScheduledBroadcastDTO>> getScheduledBroadcasts(
        @RequestParam(value = "categoryCd", required = false) String categoryCd,
        @RequestParam(value = "scheduledDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate scheduledDate) {
        log.info("call schedule");

        List<ScheduledBroadcastDTO> broadcasts = mainService.getScheduledBroadcasts(categoryCd, scheduledDate);
        if (broadcasts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(broadcasts);
    }
    @GetMapping("/live-chance")
    public ResponseEntity<List<LiveChanceCarouselDTO>> getLiveChanceCarousels(
        @RequestParam(value = "categoryCd", required = false) String categoryCd) {
        log.info("call getLiveChanceCarousels");

        List<LiveChanceCarouselDTO> broadcasts = mainService.getLiveChanceCarousels(categoryCd);
        if (broadcasts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(broadcasts);
    }

    @PostMapping("/notification-channel")
    public ResponseEntity<String> addNotificationChannel(
        @RequestParam("channelSeq") Long channelSeq) {
        log.info("call addNotificationChannel");

        boolean added = mainService.addNotificationChannel(userUUID, channelSeq);
        if (added) {
            return ResponseEntity.ok("구독 완료");
        } else {
            return ResponseEntity.status(409).body("이미 구독중입니다.");
        }
    }

    @PostMapping("/notification-channel/remove")
    public ResponseEntity<String> removeNotificationChannel(
        @RequestParam("channelSeq") Long channelSeq) {
        log.info("call removeNotificationChannel");

        boolean removed = mainService.removeNotificationChannel(userUUID, channelSeq);
        if (removed) {
            return ResponseEntity.ok("구독 취소 완료");
        } else {
            return ResponseEntity.status(404).body("구독 정보가 없습니다.");
        }
    }

    @GetMapping("/notification-channel")
    public ResponseEntity<Boolean> checkNotificationChannelExists(
        @RequestParam("channelSeq") Long channelSeq) {
        log.info("call checkNotificationChannelExists");

        boolean exists = mainService.checkNotificationChannelExists(userUUID, channelSeq);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/subscribed-channels")
    public ResponseEntity<List<SubscribedChannelDTO>> getSubscribedChannels() {
        log.info("call getSubscribedChannels");

        List<SubscribedChannelDTO> channels = mainService.getSubscribedChannels(userUUID);
        if (channels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(channels);
    }
}
