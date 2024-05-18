package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.LiveBroadcastInfoDTO;

import java.util.List;

public interface LiveBroadcastInfoService {

    LiveBroadcastInfoDTO getBasicBroadcastInfo(long broadcastId);
}
