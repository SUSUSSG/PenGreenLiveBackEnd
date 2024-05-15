package susussg.pengreenlive.broadcast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;
import susussg.pengreenlive.broadcast.mapper.BroadcastStatisticsMapper;
import susussg.pengreenlive.broadcast.service.BroadcastStatisticsService;

@Service
public class BroadcastStatisticsServiceImpl implements BroadcastStatisticsService {

    @Autowired
    private BroadcastStatisticsMapper broadcastStatisticsMapper;

    @Override
    @Transactional
    public void insertBroadcastStatistics(BroadcastStatistics broadcastStatistics) {
        broadcastStatisticsMapper.insertBroadcastStatistics(broadcastStatistics);
    }

    @Override
    @Transactional
    public void updateLikesCount(long broadcastSeq) {
        broadcastStatisticsMapper.updateLikesCount(broadcastSeq);
    }

    @Override
    @Transactional
    public void decrementLikesCount(long broadcastSeq) {
        broadcastStatisticsMapper.decrementLikesCount(broadcastSeq);
    }

    @Override
    @Transactional
    public void updateAverageViewerCount(long broadcastSeq, int averageViewerCount) {
        broadcastStatisticsMapper.updateAverageViewerCount(broadcastSeq, averageViewerCount);
    }

    @Override
    @Transactional
    public void updateMaxViewerCount(long broadcastSeq, int maxViewerCount) {
        broadcastStatisticsMapper.updateMaxViewerCount(broadcastSeq, maxViewerCount);
    }

    @Override
    @Transactional(readOnly = true)
    public BroadcastStatistics findById(long broadcastSeq) {
        return broadcastStatisticsMapper.findById(broadcastSeq);
    }

    @Override
    @Transactional
    public void updateBroadcastStatistics(long broadcastId, BroadcastStatistics statistics) {
        broadcastStatisticsMapper.updateBroadcastStatistics(broadcastId, statistics);
    }
}
