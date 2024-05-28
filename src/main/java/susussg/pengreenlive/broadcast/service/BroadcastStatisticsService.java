package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BroadcastStatisticsService {
    void insertBroadcastStatistics(BroadcastStatistics broadcastStatistics);
    void updateLikesCount(long broadcastSeq);
    void decrementLikesCount(long broadcastSeq);
    void updateAverageViewerCount(long broadcastSeq, int averageViewerCount);
    void updateMaxViewerCount(long broadcastSeq, int maxViewerCount);
    BroadcastStatistics findById(long broadcastSeq);
    void updateBroadcastStatistics(long broadcastId, BroadcastStatistics statistics);
    void incrementViewsCount(long broadcastSeq);
    List<BroadcastStatistics> getStatisticsByDateRange(String startDate, String endDate);
    List<BroadcastStatistics> getStatisticsByVendorAndDateRange(Long vendorSeq, LocalDateTime startDate, LocalDateTime endDate);

    int getAverageBroadcastDuration(Long vendorSeq, LocalDate startDate, LocalDate endDate);

    int getAverageViewerCount(Long vendorSeq, LocalDate startDate, LocalDate endDate);

    int getAveragePurchaseQuantity(Long vendorSeq, LocalDate startDate, LocalDate endDate);

    int getAverageProductClicks(Long vendorSeq, LocalDate startDate, LocalDate endDate);

    int getAverageViewingTime(Long vendorSeq, LocalDate startDate, LocalDate endDate);

    int getAverageLikesCount(Long vendorSeq, LocalDate startDate, LocalDate endDate);

    long getAveragePurchaseAmount(Long vendorSeq, LocalDate startDate, LocalDate endDate);

    void toggleLike(String userUuid, Long broadcastSeq);
    boolean isLikedByUser(String userUuid, Long broadcastSeq);
}
