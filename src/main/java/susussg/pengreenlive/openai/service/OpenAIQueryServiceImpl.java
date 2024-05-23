package susussg.pengreenlive.openai.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;
import susussg.pengreenlive.openai.dto.AiBroadcastPromptDTO;
import susussg.pengreenlive.openai.dto.RecentOrderDTO;
import susussg.pengreenlive.openai.mapper.OpenAIMapper;

@Service
public class OpenAIQueryServiceImpl implements OpenAIQueryService {

    @Autowired
    OpenAIMapper openAIMapper;

    @Override
    public List<RecentOrderDTO> getRecentOrders(String userUuid) {
        return openAIMapper.getRecentOrders(userUuid);
    }

    @Override
    public List<ScheduledBroadcastDTO> getBroadcastsByKeyword(String keyword) {
        return openAIMapper.getBroadcastsByKeyword(keyword);
    }

    @Override
    public AiBroadcastPromptDTO getBroadcastDetailsBySeq(Long broadcastSeq) {
        return openAIMapper.getBroadcastDetailsBySeq(broadcastSeq);
    }
}
