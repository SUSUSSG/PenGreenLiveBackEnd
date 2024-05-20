package susussg.pengreenlive.broadcast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.broadcast.dto.BroadcastViewerCount;
import susussg.pengreenlive.broadcast.mapper.BroadcastViewerCountMapper;

import java.util.List;

@Service
public class BroadcastViewerCountServiceImpl implements BroadcastViewerCountService {

    @Autowired
    private BroadcastViewerCountMapper broadcastViewerCountMapper;

    @Override
    public void addBroadcastViewerCount(BroadcastViewerCount broadcastViewerCount) {
        broadcastViewerCountMapper.insertBroadcastViewerCount(broadcastViewerCount);
    }

    @Override
    public List<BroadcastViewerCount> getBroadcastViewerCounts(Long broadcastSeq) {
        return broadcastViewerCountMapper.selectByBroadcastSeq(broadcastSeq);
    }
}