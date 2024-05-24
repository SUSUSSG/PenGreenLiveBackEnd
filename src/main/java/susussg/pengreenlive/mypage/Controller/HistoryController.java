package susussg.pengreenlive.mypage.Controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.mypage.DTO.HistoryDTO;
import susussg.pengreenlive.mypage.Service.HistoryService;

@RestController
@Log4j2
@RequestMapping("/api")
public class HistoryController {

  @Autowired
  private final HistoryService historyService;

  public HistoryController(HistoryService historyService) {
    this.historyService = historyService;
  }

  @GetMapping("/recently-viewed/broadcasts")
  public List<HistoryDTO> getRecentBroadcasts(@RequestParam String userUUID) {
    return historyService.getRecentBroadcasts(userUUID);
  }
}
