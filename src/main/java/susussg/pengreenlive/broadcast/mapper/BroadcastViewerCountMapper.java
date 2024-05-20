package susussg.pengreenlive.broadcast.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import susussg.pengreenlive.broadcast.dto.BroadcastViewerCount;

import java.util.List;

@Mapper
public interface BroadcastViewerCountMapper {

    void insertBroadcastViewerCount(BroadcastViewerCount broadcastViewerCount);

    List<BroadcastViewerCount> selectByBroadcastSeq(@Param("broadcastSeq") Long broadcastSeq);
}
