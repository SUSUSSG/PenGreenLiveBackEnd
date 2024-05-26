package susussg.pengreenlive.mypage.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.mypage.DTO.HistoryDTO;
import susussg.pengreenlive.mypage.Mapper.HistoryMapper;

@Service
public class HistoryServiceImpl implements HistoryService{

  private final HistoryMapper historyMapper;

  @Autowired
  public HistoryServiceImpl(HistoryMapper historyMapper) {
    this.historyMapper = historyMapper;
  }

  @Override
  public List<HistoryDTO> getRecentBroadcasts(String userUUID) {
    return historyMapper.getRecentBroadcasts(userUUID);
  }

  @Override
  public void saveUserBroadcastHistory(HistoryDTO historyDTO) {
    int result = historyMapper.insertUserBroadcastHistory(historyDTO);
    if (result != 1) {
      throw new RuntimeException("save user broadcast history failed");
    }
  }
}
