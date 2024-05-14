package susussg.pengreenlive.broadcast.mapper;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class BroadcastStatisticsMapperTest {

    @Autowired
    private BroadcastStatisticsMapper broadcastStatisticsMapper;

    @Test
    public void contextLoads() {
        assertNotNull(broadcastStatisticsMapper);
    }

    @Test
    public void testUpdateLikesCount() {
        int broadcastSeq = 6;
        broadcastStatisticsMapper.updateLikesCount(broadcastSeq);
    }

    @Test
    public void testDecrementLikesCount(){
        int broadcastSeq = 6;
        broadcastStatisticsMapper.decrementLikesCount(broadcastSeq);
    }

    @Test
    public void testUpdateAverageViewerCount() {
        long broadcastSeq = 6L;
        int averageViewerCount = 100;
        broadcastStatisticsMapper.updateAverageViewerCount(broadcastSeq, averageViewerCount);
    }

    @Test
    public void testUpdateMaxViewerCount() {
        long broadcastSeq = 6L;
        int maxViewerCount = 200;
        broadcastStatisticsMapper.updateMaxViewerCount(broadcastSeq, maxViewerCount);
    }
    @Test
    public void testInsertBroadcastStatistics() {
        BroadcastStatistics broadcastStatistics = new BroadcastStatistics(
                7L,
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

        broadcastStatisticsMapper.insertBroadcastStatistics(broadcastStatistics);

        BroadcastStatistics result = broadcastStatisticsMapper.findById(7L);
        assertNotNull(result);
        assertEquals(7L, result.getBroadcastSeq());
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
}
