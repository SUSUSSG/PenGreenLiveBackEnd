package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.*;

import java.util.List;

public interface LiveRegisterService {
    void registerBroadcast(BroadcastDTO broadcastDTO);
    void registerBroadcastProduct(BroadcastProductDTO broadcastProductDTO);
    void registerNotice(NoticeDTO noticeDTO);
    void registerFaq(FaqDTO faqDTO);
    void registerBenefit(BenefitDTO benefitDTO);
    List<ChannelSalesProductDTO> getChannelSalesProductAll(long vendorId);
}
