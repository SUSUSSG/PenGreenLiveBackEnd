package susussg.pengreenlive.broadcast.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface ProductClickService {
    void addProductClick(Long broadcastSeq, Long productSeq);

    void addProductClicksForBroadcast(Long broadcastSeq);
    void incrementProductClick(Long broadcastSeq, Long productSeq);
    void updateAverageProductClicks(Long broadcastSeq);

}
