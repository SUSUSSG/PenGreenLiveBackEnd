package susussg.pengreenlive.mypage.Service;

import java.util.List;
import susussg.pengreenlive.mypage.DTO.HistoryDTO;

public interface HistoryService {

  List<HistoryDTO> getRecentBroadcasts(String userUUID);

  void saveUserBroadcastHistory(HistoryDTO historyDTO);

}
