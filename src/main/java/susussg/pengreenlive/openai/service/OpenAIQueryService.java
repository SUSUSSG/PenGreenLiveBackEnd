package susussg.pengreenlive.openai.service;

import java.util.List;
import susussg.pengreenlive.main.DTO.ScheduledBroadcastDTO;
import susussg.pengreenlive.openai.dto.AiBroadcastPromptDTO;
import susussg.pengreenlive.openai.dto.RecentOrderDTO;

public interface OpenAIQueryService {
    List<RecentOrderDTO> getRecentOrders(String userUuid);
    List<ScheduledBroadcastDTO> getBroadcastsByKeyword(String keyword);
    List<AiBroadcastPromptDTO> getBroadcastDetailsBySeq(Long broadcastSeq);

}
