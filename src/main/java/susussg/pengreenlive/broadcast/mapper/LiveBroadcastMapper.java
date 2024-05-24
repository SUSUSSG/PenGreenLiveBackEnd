package susussg.pengreenlive.broadcast.mapper;

import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.broadcast.dto.*;

import java.util.List;

@Mapper
public interface LiveBroadcastMapper {
    BroadcastDTO selectBroadcast(long broadcastId);

    List<NoticeDTO> selectNotice(long broadcastId);

    List<BenefitDTO> selectBenefit(long broadcastId);

    List<FaqDTO> selectFaq(long broadcastId);

    List<LiveBroadcastProductDTO> selectBroadcastProduct(long broadcastId);


    int insertNotice(NoticeDTO notice);
    NoticeDTO selectNoticeRecent(long broadcastId);
    void deleteNotice(long noticeId);

    int insertFaq(FaqDTO faq);
    FaqDTO selectFaqRecent(long broadcastId);
    void deleteFaq(long faqId);

    LiveProductStatsDTO selectLiveProductStats(long broadcastId, long productId);
}
