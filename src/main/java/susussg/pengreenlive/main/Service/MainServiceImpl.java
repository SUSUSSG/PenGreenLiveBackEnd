package susussg.pengreenlive.main.Service;

import java.util.List;
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
    public List<ScheduledBroadcastDTO> getScheduledBroadcasts(String categoryCd) {
        return mainMapper.selectScheduledBroadcasts(categoryCd);
    }

    @Override
    public List<LiveChanceCarouselDTO> getLiveChanceCarousels(String categoryCd) {
        return mainMapper.selectLiveChanceCarousels(categoryCd);
    }
}
