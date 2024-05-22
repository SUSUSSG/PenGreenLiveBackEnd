package susussg.pengreenlive.broadcast.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.broadcast.dto.*;
import susussg.pengreenlive.broadcast.mapper.LiveBroadcastMapper;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class LiveBroadcastServiceImpl implements LiveBroadcastService {

    @Autowired
    private final LiveBroadcastMapper liveBroadcastMapper;

    @Override
    public LiveBroadcastInfoDTO getBroadcastInfo(long broadcastId) {

        LiveBroadcastInfoDTO liveBroadcastInfoDTO = new LiveBroadcastInfoDTO();

        liveBroadcastInfoDTO.setBroadcast(liveBroadcastMapper.selectBroadcast(broadcastId));
        liveBroadcastInfoDTO.setNotices(liveBroadcastMapper.selectNotice(broadcastId));
        liveBroadcastInfoDTO.setBenefits(liveBroadcastMapper.selectBenefit(broadcastId));
        liveBroadcastInfoDTO.setFaqs(liveBroadcastMapper.selectFaq(broadcastId));

        return liveBroadcastInfoDTO;
    }

    @Override
    public List<LiveBroadcastProductDTO> getBroadcastProducts(long broadcastId) {
        return liveBroadcastMapper.selectBroadcastProduct(broadcastId);
    }


    @Override
    @Transactional
    public NoticeDTO addNotice(NoticeDTO notice) {
        int result = liveBroadcastMapper.insertNotice(notice);
        if (result == 0) {
            throw new RuntimeException("notice insert failed");
        }
        return liveBroadcastMapper.selectNoticeRecent(notice.getBroadcastSeq());
    }

    @Override
    public void removeNotice(long noticeId) {
        liveBroadcastMapper.deleteNotice(noticeId);
    }

    @Override
    @Transactional
    public FaqDTO addFaq(FaqDTO faq) {
        int result = liveBroadcastMapper.insertFaq(faq);
        if (result == 0) {
            throw new RuntimeException("faq insert failed");
        }
        return liveBroadcastMapper.selectFaqRecent(faq.getBroadcastSeq());
    }

    @Override
    public void removeFaq(long faqId) {
        liveBroadcastMapper.deleteFaq(faqId);
    }
}
