package susussg.pengreenlive.broadcast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.broadcast.dto.WatchTime;
import susussg.pengreenlive.broadcast.mapper.BroadcastStatisticsMapper;
import susussg.pengreenlive.broadcast.mapper.WatchTimeMapper;

@Service
public class WatchTimeServiceImpl implements WatchTimeService {

    @Autowired
    private WatchTimeMapper watchTimeMapper;
    @Autowired
    private BroadcastStatisticsMapper broadcastStatisticsMapper;

    @Override
    public void addWatchTime(WatchTime watchTime) {
        watchTimeMapper.insertWatchTime(watchTime);
    }

    @Transactional
    @Override
    public void calculateAndDeleteWatchTime(Long broadcastSeq) {
        // 시청 시간 계산
        Integer avgViewingTime = watchTimeMapper.calculateAvgViewingTime(broadcastSeq);

        // 통계 테이블 업데이트
        if(avgViewingTime == null){
            broadcastStatisticsMapper.updateAvgViewingTime(broadcastSeq, 0);
        }
        else {
            broadcastStatisticsMapper.updateAvgViewingTime(broadcastSeq, avgViewingTime);
        }

        // 사용된 데이터 삭제
        watchTimeMapper.deleteWatchTimeData(broadcastSeq);
    }
}
