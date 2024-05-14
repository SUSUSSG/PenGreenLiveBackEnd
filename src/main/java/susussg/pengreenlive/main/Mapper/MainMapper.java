package susussg.pengreenlive.main.Mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.main.DTO.LiveChanceCarouselDTO;
import susussg.pengreenlive.main.DTO.MainCarouselDTO;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;

@Mapper
public interface MainMapper {
    List<MainCarouselDTO> selectMainCarousels();
    List<ScheduledBroadcastDTO> selectScheduledBroadcasts(Map<String, Object> params);
    List<LiveChanceCarouselDTO> selectLiveChanceCarousels(String categoryCd);
}
