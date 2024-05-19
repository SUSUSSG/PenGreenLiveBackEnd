package susussg.pengreenlive.service;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.mypage.DTO.HistoryDTO;
import susussg.pengreenlive.mypage.Service.HistoryService;

@Log4j2
@SpringBootTest
public class HistoryServiceTest {

  private final HistoryService historyService;

  @Autowired
  public HistoryServiceTest(HistoryService historyService) {
    this.historyService = historyService;
  }

  @Test
  public void getrecentbroadcastListTest(){
    String userUUID = "f23a72e0-1347-11ef-b085-f220affc9a21";
    List<HistoryDTO> historyDTOList = historyService.getRecentBroadcasts(userUUID);

    log.info("최근 본 방송 목록 : " + historyDTOList);
  }
}
