package susussg.pengreenlive.broadcast.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BroadcastStatisticsMapper {

    void insertBroadcastStatistics(BroadcastStatistics broadcastStatistics);

    void decrementLikesCount(long broadcastSeq);

    void updateLikesCount(long broadcastSeq);

    void updateAverageViewerCount(@Param("broadcastSeq") long broadcastSeq, @Param("averageViewerCount") int averageViewerCount);

    void updateMaxViewerCount(@Param("broadcastSeq") long broadcastSeq, @Param("maxViewerCount") int maxViewerCount);

    BroadcastStatistics findById(long broadcastSeq);

    void updateBroadcastStatistics(@Param("broadcastId") long broadcastId, @Param("statistics") BroadcastStatistics statistics);

    void incrementViewsCount(@Param("broadcastSeq") long broadcastSeq);

    List<BroadcastStatistics> getStatisticsByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<BroadcastStatistics> getStatisticsByVendorAndDateRange(
            @Param("vendorSeq") long vendorSeq,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    int getAverageBroadcastDuration(@Param("vendorSeq") long vendorSeq,
                                    @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);

    int getAverageViewerCount(@Param("vendorSeq") long vendorSeq,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate);

    int getAveragePurchaseQuantity(@Param("vendorSeq") long vendorSeq,
                                   @Param("startDate") LocalDate startDate,
                                   @Param("endDate") LocalDate endDate);

    int getAverageProductClicks(@Param("vendorSeq") long vendorSeq,
                                @Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate);

    int getAverageViewingTime(@Param("vendorSeq") long vendorSeq,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate);

    int getAverageLikesCount(@Param("vendorSeq") long vendorSeq,
                             @Param("startDate") LocalDate startDate,
                             @Param("endDate") LocalDate endDate);

    long getAveragePurchaseAmount(@Param("vendorSeq") long vendorSeq,
                                  @Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);

    void updateAvgViewingTime(@Param("broadcastSeq") Long broadcastSeq, @Param("avgViewingTime") Integer avgViewingTime);

    Integer checkUserLike(@Param("userUuid") String userUuid, @Param("broadcastSeq") Long broadcastSeq);

    void addUserLike(@Param("userUuid") String userUuid, @Param("broadcastSeq") Long broadcastSeq);

    void removeUserLike(@Param("userUuid") String userUuid, @Param("broadcastSeq") Long broadcastSeq);
}
