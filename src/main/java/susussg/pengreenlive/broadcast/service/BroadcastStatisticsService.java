package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;

public interface BroadcastStatisticsService {
    void insertBroadcastStatistics(BroadcastStatistics broadcastStatistics);
    void updateLikesCount(long broadcastSeq);
    void decrementLikesCount(long broadcastSeq);
    void updateAverageViewerCount(long broadcastSeq, int averageViewerCount);
    void updateMaxViewerCount(long broadcastSeq, int maxViewerCount);
    BroadcastStatistics findById(long broadcastSeq);
}
