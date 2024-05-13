package susussg.pengreenlive.main.Controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.main.DTO.LiveChanceCarouselDTO;
import susussg.pengreenlive.main.DTO.MainCarouselDTO;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;
import susussg.pengreenlive.main.Service.MainService;

@Log4j2
@RestController
public class MainController {

    @Autowired
    MainService mainService;

    @GetMapping("/main-carousels")
    public List<MainCarouselDTO> getMainCarousels(){
        log.info("call getMainCarousels");
        return mainService.getMainCarousels();
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<ScheduledBroadcastDTO>> getScheduledBroadcasts(
        @RequestParam(value = "categoryCd", required = false) String categoryCd) {
        log.info("call schedule");

        List<ScheduledBroadcastDTO> broadcasts = mainService.getScheduledBroadcasts(categoryCd);
        log.info(broadcasts.toString());
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
        log.info(broadcasts.toString());
        if (broadcasts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(broadcasts);
    }
}
