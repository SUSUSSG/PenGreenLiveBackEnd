package susussg.pengreenlive.main.Service;

import java.util.List;
import susussg.pengreenlive.main.DTO.MainCarouselDTO;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;

public interface MainService {

    List<MainCarouselDTO> getMainCarousels();

    List<ScheduledBroadcastDTO> getScheduledBroadcasts(String categoryCd);
}
