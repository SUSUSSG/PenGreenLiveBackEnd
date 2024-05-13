package susussg.pengreenlive.main.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.main.DTO.LiveChanceCarouselDTO;
import susussg.pengreenlive.main.DTO.MainCarouselDTO;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;

@Mapper
public interface MainMapper {
    List<MainCarouselDTO> selectMainCarousels();
    List<ScheduledBroadcastDTO> selectScheduledBroadcasts(String categoryCd);
    List<LiveChanceCarouselDTO> selectLiveChanceCarousels(String categoryCd);
}
