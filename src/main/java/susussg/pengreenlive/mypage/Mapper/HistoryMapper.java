package susussg.pengreenlive.mypage.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.mypage.DTO.HistoryDTO;

@Mapper
public interface HistoryMapper {

  List<HistoryDTO> getRecentBroadcasts(String userUUID);

  int insertUserBroadcastHistory(HistoryDTO history);
}
