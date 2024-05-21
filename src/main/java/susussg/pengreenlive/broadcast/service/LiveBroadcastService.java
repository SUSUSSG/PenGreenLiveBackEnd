package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.LiveBroadcastInfoDTO;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastProductDTO;

import java.util.List;

public interface LiveBroadcastService {

    LiveBroadcastInfoDTO getBroadcastInfo(long broadcastId);

    List<LiveBroadcastProductDTO> getBroadcastProducts(long broadcastId);

    void addNotice(long broadcastId, String noticeContent);
}
