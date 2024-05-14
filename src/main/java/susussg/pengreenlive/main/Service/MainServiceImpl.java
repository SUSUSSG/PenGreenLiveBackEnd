package susussg.pengreenlive.main.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.main.DTO.LiveChanceCarouselDTO;
import susussg.pengreenlive.main.DTO.MainCarouselDTO;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;
import susussg.pengreenlive.main.Mapper.MainMapper;

@Service
public class MainServiceImpl implements MainService {

    @Autowired
    MainMapper mainMapper;

    @Transactional(readOnly = true)
    public List<MainCarouselDTO> getMainCarousels() {
        return mainMapper.selectMainCarousels();
    }


    @Override
    public List<ScheduledBroadcastDTO> getScheduledBroadcasts(String categoryCd, LocalDate scheduledDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryCd", categoryCd);
        params.put("scheduledDate", scheduledDate);
        return mainMapper.selectScheduledBroadcasts(params);
    }


    @Override
    public List<LiveChanceCarouselDTO> getLiveChanceCarousels(String categoryCd) {
        return mainMapper.selectLiveChanceCarousels(categoryCd);
    }
    @Override
    @Transactional
    public boolean addNotificationChannel(String userUuid, Long channelSeq) {
        Integer count = mainMapper.checkNotificationChannelExists(userUuid, channelSeq);
        if (count == null || count == 0) {
            mainMapper.insertNotificationChannel(userUuid, channelSeq);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean removeNotificationChannel(String userUuid, Long channelSeq) {
        Integer count = mainMapper.checkNotificationChannelExists(userUuid, channelSeq);
        if (count != null && count > 0) {
            mainMapper.deleteNotificationChannel(userUuid, channelSeq);
            return true;
        }
        return false;
    }
}
