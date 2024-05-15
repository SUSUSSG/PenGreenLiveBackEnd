package susussg.pengreenlive.broadcast.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;

@Mapper
public interface BroadcastStatisticsMapper {

    void insertBroadcastStatistics(BroadcastStatistics broadcastStatistics);
    void decrementLikesCount(long broadcastSeq);
    void updateLikesCount(long broadcastSeq);
    void updateAverageViewerCount(@Param("broadcastSeq") long broadcastSeq,@Param("averageViewerCount") int averageViewerCount);
    void updateMaxViewerCount(@Param("broadcastSeq")long broadcastSeq, @Param("maxViewerCount") int maxViewerCount);
    BroadcastStatistics findById(long broadcastSeq);
    void updateBroadcastStatistics(@Param("broadcastId") long broadcastId, @Param("statistics") BroadcastStatistics statistics);
}
