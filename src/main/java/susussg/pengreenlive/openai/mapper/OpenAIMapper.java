package susussg.pengreenlive.openai.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;
import susussg.pengreenlive.openai.dto.RecentOrderDTO;

@Mapper
public interface OpenAIMapper {

    List<RecentOrderDTO> getRecentOrders(String userUuid);

    List<ScheduledBroadcastDTO> getBroadcastsByKeyword(String keyword);

}
