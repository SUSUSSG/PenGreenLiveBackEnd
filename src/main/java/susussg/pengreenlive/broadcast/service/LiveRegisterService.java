package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.*;

import java.util.List;

public interface LiveRegisterService {
    String getChannelName(long vendorId);
    List<BroadcastCategoryDTO> getAllCategory();
    void saveBroadcast(BroadcastDTO broadcastDTO);
    void saveBroadcastProduct(BroadcastProductDTO broadcastProductDTO);
    void saveNotice(NoticeDTO noticeDTO);
    void saveFaq(FaqDTO faqDTO);
    void saveBenefit(BenefitDTO benefitDTO);
    List<ChannelSalesProductDTO> getChannelSalesProductAll(long vendorId);
}
