package susussg.pengreenlive.main.Mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import susussg.pengreenlive.main.DTO.LiveChanceCarouselDTO;
import susussg.pengreenlive.main.DTO.MainCarouselDTO;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;
import susussg.pengreenlive.main.DTO.SubscribedChannelDTO;

@Mapper
public interface MainMapper {
    List<MainCarouselDTO> selectMainCarousels();
    List<ScheduledBroadcastDTO> selectScheduledBroadcasts(Map<String, Object> params);
    List<LiveChanceCarouselDTO> selectLiveChanceCarousels(String categoryCd);
    int insertNotificationChannel(@Param("userUuid") String userUuid, @Param("channelSeq") Long channelSeq);
    Integer checkNotificationChannelExists(@Param("userUuid") String userUuid, @Param("channelSeq") Long channelSeq);
    int deleteNotificationChannel(@Param("userUuid") String userUuid, @Param("channelSeq") Long channelSeq);
    List<SubscribedChannelDTO> selectSubscribedChannels(@Param("userUuid") String userUuid);

}
