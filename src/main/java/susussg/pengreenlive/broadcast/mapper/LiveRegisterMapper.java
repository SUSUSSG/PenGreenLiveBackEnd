package susussg.pengreenlive.broadcast.mapper;

import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.broadcast.dto.*;

@Mapper
public interface LiveRegisterMapper {
    int insertBroadcast(BroadcastDTO broadcastDTO);
    int insertBroadcastProduct(BroadcastProductDTO broadcastProductDTO);
    int insertNotice(NoticeDTO noticeDTO);
    int insertFaq(FaqDTO faqDTO);
    int insertBroadcastBenefit(BroadcastBenefitDTO broadcastBenefitDTO);
}
