package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.BroadcastViewerCount;

import java.util.List;

public interface BroadcastViewerCountService {
    void addBroadcastViewerCount(BroadcastViewerCount broadcastViewerCount);
    List<BroadcastViewerCount> getBroadcastViewerCounts(Long broadcastSeq);
}