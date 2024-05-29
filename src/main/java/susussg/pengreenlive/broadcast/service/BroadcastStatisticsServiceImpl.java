package susussg.pengreenlive.broadcast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;
import susussg.pengreenlive.broadcast.mapper.BroadcastStatisticsMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    @Transactional
    public void incrementViewsCount(long broadcastSeq) {
        broadcastStatisticsMapper.incrementViewsCount(broadcastSeq);
    }

    @Override
    public List<BroadcastStatistics> getStatisticsByDateRange(String startDate, String endDate) {
        return broadcastStatisticsMapper.getStatisticsByDateRange(startDate, endDate);
    }

    @Override
    public List<BroadcastStatistics> getStatisticsByVendorAndDateRange(Long vendorSeq, LocalDateTime startDate, LocalDateTime endDate) {
        return broadcastStatisticsMapper.getStatisticsByVendorAndDateRange(vendorSeq, startDate, endDate);
    }

    @Override
    public int getAverageBroadcastDuration(Long vendorSeq, LocalDate startDate, LocalDate endDate) {
        return broadcastStatisticsMapper.getAverageBroadcastDuration(vendorSeq, startDate, endDate);
    }

    @Override
    public int getAverageViewerCount(Long vendorSeq, LocalDate startDate, LocalDate endDate) {
        return broadcastStatisticsMapper.getAverageViewerCount(vendorSeq, startDate, endDate);
    }

    @Override
    public int getAveragePurchaseQuantity(Long vendorSeq, LocalDate startDate, LocalDate endDate) {
        return broadcastStatisticsMapper.getAveragePurchaseQuantity(vendorSeq, startDate, endDate);
    }

    @Override
    public int getAverageProductClicks(Long vendorSeq, LocalDate startDate, LocalDate endDate) {
        return broadcastStatisticsMapper.getAverageProductClicks(vendorSeq, startDate, endDate);
    }

    @Override
    public int getAverageViewingTime(Long vendorSeq, LocalDate startDate, LocalDate endDate) {
        return broadcastStatisticsMapper.getAverageViewingTime(vendorSeq, startDate, endDate);
    }

    @Override
    public int getAverageLikesCount(Long vendorSeq, LocalDate startDate, LocalDate endDate) {
        return broadcastStatisticsMapper.getAverageLikesCount(vendorSeq, startDate, endDate);
    }

    @Override
    public long getAveragePurchaseAmount(Long vendorSeq, LocalDate startDate, LocalDate endDate) {
        return broadcastStatisticsMapper.getAveragePurchaseAmount(vendorSeq, startDate, endDate);
    }

    @Override
    @Transactional
    public void toggleLike(String userUuid, Long broadcastSeq) {
        if (broadcastStatisticsMapper.checkUserLike(userUuid, broadcastSeq) > 0) {
            broadcastStatisticsMapper.decrementLikesCount(broadcastSeq);
            broadcastStatisticsMapper.removeUserLike(userUuid, broadcastSeq);
        } else {
            broadcastStatisticsMapper.updateLikesCount(broadcastSeq);
            broadcastStatisticsMapper.addUserLike(userUuid, broadcastSeq);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isLikedByUser(String userUuid, Long broadcastSeq) {
        return broadcastStatisticsMapper.checkUserLike(userUuid, broadcastSeq) > 0;
    }
}
