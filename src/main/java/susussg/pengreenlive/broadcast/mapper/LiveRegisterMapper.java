package susussg.pengreenlive.broadcast.mapper;

import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.broadcast.dto.*;

import java.util.List;

@Mapper
public interface LiveRegisterMapper {
    String selectChannelName(long vendorId);
    List<BroadcastCategoryDTO> selectAllCategory();
    int insertBroadcast(BroadcastDTO broadcastDTO);
    int insertBroadcastProduct(BroadcastProductDTO broadcastProductDTO);
    int insertNotice(NoticeDTO noticeDTO);
    int insertFaq(FaqDTO faqDTO);
    int insertBenefit(BenefitDTO BenefitDTO);
    List<ChannelSalesProductDTO> selectChannelSalesProduct(long vendorId);

}
