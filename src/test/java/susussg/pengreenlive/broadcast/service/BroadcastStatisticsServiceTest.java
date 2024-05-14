package susussg.pengreenlive.broadcast.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class BroadcastStatisticsServiceTest {

    @Autowired
    private BroadcastStatisticsService broadcastStatisticsService;

    @Test
    public void testInsertBroadcastStatistics() {
        BroadcastStatistics broadcastStatistics = new BroadcastStatistics(
                6L,
                100,
                200,
                50,
                10000L,
                30,
                60,
                15,
                500000L,
                20
        );

        broadcastStatisticsService.insertBroadcastStatistics(broadcastStatistics);

        BroadcastStatistics result = broadcastStatisticsService.findById(6L);
        assertNotNull(result);
        assertEquals(6L, result.getBroadcastSeq());
        assertEquals(100, result.getAvgViewerCount());
        assertEquals(200, result.getMaxViewerCount());
        assertEquals(50, result.getLikesCount());
        assertEquals(10000L, result.getAvgPurchaseAmount());
        assertEquals(30, result.getAvgViewingTime());
        assertEquals(60, result.getBroadcastDuration());
        assertEquals(15, result.getAvgProductClicks());
        assertEquals(500000L, result.getTotalSalesAmount());
        assertEquals(20, result.getTotalSalesQty());
    }

    @Test
    public void testUpdateLikesCount() {
        broadcastStatisticsService.updateLikesCount(6L);
        BroadcastStatistics result = broadcastStatisticsService.findById(6L);
        assertNotNull(result);
        assertEquals(2, result.getLikesCount());
    }

    @Test
    public void testDecrementLikesCount(){
        broadcastStatisticsService.decrementLikesCount(6L);
        BroadcastStatistics result = broadcastStatisticsService.findById(6L);
        assertEquals(0, result.getLikesCount());
    }

    @Test
    public void testUpdateAverageViewerCount() {
        broadcastStatisticsService.updateAverageViewerCount(6L, 150);
        BroadcastStatistics result = broadcastStatisticsService.findById(6L);
        assertNotNull(result);
        assertEquals(150, result.getAvgViewerCount());
    }

    @Test
    public void testUpdateMaxViewerCount() {
        broadcastStatisticsService.updateMaxViewerCount(6L, 250);
        BroadcastStatistics result = broadcastStatisticsService.findById(6L);
        assertNotNull(result);
        assertEquals(250, result.getMaxViewerCount());
    }
}
