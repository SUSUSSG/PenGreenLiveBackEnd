package susussg.pengreenlive.broadcast.mapper;

import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.broadcast.dto.BenefitDTO;
import susussg.pengreenlive.broadcast.dto.BroadcastDTO;
import susussg.pengreenlive.broadcast.dto.FaqDTO;
import susussg.pengreenlive.broadcast.dto.NoticeDTO;

import java.util.List;

@Mapper
public interface LiveBroadcastMapper {
    BroadcastDTO selectBroadcast(long broadcastId);

    List<NoticeDTO> selectNotice(long broadcastId);

    List<BenefitDTO> selectBenefit(long broadcastId);

    List<FaqDTO> selectFaq(long broadcastId);

}
