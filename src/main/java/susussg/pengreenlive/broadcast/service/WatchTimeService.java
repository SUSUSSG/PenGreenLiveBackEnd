package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.WatchTime;

public interface WatchTimeService {
    void addWatchTime(WatchTime watchTime);
    void calculateAndDeleteWatchTime(Long broadcastSeq);
}