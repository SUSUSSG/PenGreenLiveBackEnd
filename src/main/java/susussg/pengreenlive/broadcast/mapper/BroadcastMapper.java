package susussg.pengreenlive.broadcast.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import susussg.pengreenlive.broadcast.dto.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BroadcastMapper {
    String selectChannelName(Long vendorSeq);
    List<BroadcastCategoryDTO> selectAllCategory();
    int insertBroadcast(BroadcastDTO broadcastDTO);
    int insertBroadcastProduct(BroadcastProductDTO broadcastProductDTO);
    int insertNotice(NoticeDTO noticeDTO);
    int insertFaq(FaqDTO faqDTO);
    int insertBenefit(BenefitDTO BenefitDTO);
    List<ChannelSalesProductDTO> selectChannelSalesProduct(Long vendorSeq);

    // 방송 예정 정보
    List<PrepareBroadcastInfoDTO> selectPrepareBroadcastInfo(Long vendorSeq);
    List<BroadcastDTO> getBroadcastsByVendorAndDateRange(@Param("vendorSeq") long vendorSeq,
                                                         @Param("startDateTime") LocalDateTime startDateTime,
                                                         @Param("endDateTime") LocalDateTime endDateTime);

    // 방송 시작 및 종료
    int updateBroadcastStartTime(BroadcastTimeDTO broadcastTimeDTO);
    int updateBroadcastEndTime(BroadcastTimeDTO broadcastTimeDTO);

}
