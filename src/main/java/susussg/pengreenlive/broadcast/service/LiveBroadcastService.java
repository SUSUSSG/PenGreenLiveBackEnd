package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.LiveBroadcastInfoDTO;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastProductDTO;
import susussg.pengreenlive.broadcast.dto.NoticeDTO;

import java.util.List;

public interface LiveBroadcastService {

    LiveBroadcastInfoDTO getBroadcastInfo(long broadcastId);

    List<LiveBroadcastProductDTO> getBroadcastProducts(long broadcastId);

    NoticeDTO addNotice(NoticeDTO notice);
}
