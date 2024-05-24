package susussg.pengreenlive.broadcast.service;

import susussg.pengreenlive.broadcast.dto.*;

import java.util.List;

public interface LiveBroadcastService {

    LiveBroadcastInfoDTO getBroadcastInfo(long broadcastId);

    List<LiveBroadcastProductDTO> getBroadcastProducts(long broadcastId);

    NoticeDTO addNotice(NoticeDTO notice);

    void removeNotice(long noticeId);

    FaqDTO addFaq(FaqDTO faq);
    void removeFaq(long faqId);

    List<NoticeDTO> getAllNotice(long broadcastId);
    List<FaqDTO> getAllFaq(long broadcastId);

    LiveProductStatsDTO getLiveProductStats(long broadcastId, long productId);
}
