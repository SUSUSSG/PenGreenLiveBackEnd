package susussg.pengreenlive.broadcast.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.broadcast.dto.*;
import susussg.pengreenlive.broadcast.mapper.LiveBroadcastInfoMapper;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class LiveBroadcastServiceImpl implements LiveBroadcastInfoService{

    @Autowired
    private final LiveBroadcastInfoMapper liveBroadcastInfoMapper;

    @Override
    public LiveBroadcastInfoDTO getBasicBroadcastInfo(long broadcastId) {

        LiveBroadcastInfoDTO liveBroadcastInfoDTO = new LiveBroadcastInfoDTO();

        liveBroadcastInfoDTO.setBroadcast(liveBroadcastInfoMapper.selectBroadcast(broadcastId));
        liveBroadcastInfoDTO.setNotices(liveBroadcastInfoMapper.selectNotice(broadcastId));
        liveBroadcastInfoDTO.setBenefits(liveBroadcastInfoMapper.selectBenefit(broadcastId));
        liveBroadcastInfoDTO.setFaqs(liveBroadcastInfoMapper.selectFaq(broadcastId));

        return liveBroadcastInfoDTO;
    }
}
