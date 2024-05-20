package susussg.pengreenlive.broadcast.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import susussg.pengreenlive.broadcast.dto.WatchTime;

@Mapper
public interface WatchTimeMapper {

    void insertWatchTime(WatchTime watchTime);
    void deleteWatchTimeData(@Param("broadcastSeq") Long broadcastSeq);
    Integer calculateAvgViewingTime(@Param("broadcastSeq") Long broadcastSeq);

}
