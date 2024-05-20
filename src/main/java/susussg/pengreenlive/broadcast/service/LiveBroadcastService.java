package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.LiveBroadcastInfoDTO;

public interface LiveBroadcastService {

    LiveBroadcastInfoDTO getBroadcastInfo(long broadcastId);
}
