package susussg.pengreenlive.broadcast.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.broadcast.dto.*;
import susussg.pengreenlive.broadcast.mapper.LiveBroadcastMapper;

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
}
