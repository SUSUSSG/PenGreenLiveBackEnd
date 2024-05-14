package susussg.pengreenlive.main.Service;

import java.time.LocalDate;
import java.util.List;
import susussg.pengreenlive.main.DTO.LiveChanceCarouselDTO;
import susussg.pengreenlive.main.DTO.MainCarouselDTO;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;

public interface MainService {

    List<MainCarouselDTO> getMainCarousels();

    List<ScheduledBroadcastDTO> getScheduledBroadcasts(String categoryCd, LocalDate scheduledDate);
    List<LiveChanceCarouselDTO> getLiveChanceCarousels(String categoryCd);
    boolean addNotificationChannel(String userUuid, Long channelSeq);
    boolean removeNotificationChannel(String userUuid, Long channelSeq);
    boolean checkNotificationChannelExists(String userUuid, Long channelSeq);


}
