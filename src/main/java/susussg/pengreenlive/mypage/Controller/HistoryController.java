package susussg.pengreenlive.mypage.Controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import susussg.pengreenlive.login.service.SecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.mypage.DTO.HistoryDTO;
import susussg.pengreenlive.mypage.Service.HistoryService;

@RestController
@Log4j2
@RequestMapping("/api")
public class HistoryController {

  @Autowired
  private final HistoryService historyService;

  @Autowired
  private SecurityService securityService;

  public HistoryController(HistoryService historyService) {
    this.historyService = historyService;
  }

  @GetMapping("/recently-viewed/broadcasts")
  public List<HistoryDTO> getRecentBroadcasts() {
    String userUUID = securityService.getCurrentUserUuid();
    return historyService.getRecentBroadcasts(userUUID);
  }
  @PostMapping("/user/view-history")
  public ResponseEntity<String> saveUserBroadcastHistory(@RequestBody HistoryDTO historyDTO) {
    String userUUID = securityService.getCurrentUserUuid();
    if (userUUID==null) {
      ResponseEntity.ok("비회원 시청");
      return null;
    }

    historyDTO.setUserUUID(userUUID);
    historyService.saveUserBroadcastHistory(historyDTO);
    return ResponseEntity.ok("사용자 시청 기록 저장");
  }
}
