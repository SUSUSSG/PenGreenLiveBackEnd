package susussg.pengreenlive.openai.service;

import java.util.List;
import susussg.pengreenlive.openai.dto.RecentOrderDTO;

public interface OpenAIQueryService {
    List<RecentOrderDTO> getRecentOrders(String userUuid);
}
