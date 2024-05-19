package susussg.pengreenlive.broadcast.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(50, result.getViewsCount());
        assertEquals(10000L, result.getAvgPurchaseAmount());
        assertEquals(30, result.getAvgViewingTime());
        assertEquals(60, result.getBroadcastDuration());
        assertEquals(15, result.getAvgProductClicks());
        assertEquals(500000L, result.getTotalSalesAmount());
        assertEquals(20, result.getTotalSalesQty());
    }

    @Test
    public void testUpdateBroadcastStatistics() {
        BroadcastStatistics broadcastStatistics = new BroadcastStatistics();
        broadcastStatistics.setBroadcastSeq(6L);
        broadcastStatistics.setAvgViewerCount(150);
        broadcastStatistics.setMaxViewerCount(250);
        broadcastStatistics.setBroadcastDuration(3600);

        broadcastStatisticsMapper.updateBroadcastStatistics(6L, broadcastStatistics);

        BroadcastStatistics result = broadcastStatisticsMapper.findById(6L);
        assertNotNull(result);
        assertEquals(150, result.getAvgViewerCount());
        assertEquals(250, result.getMaxViewerCount());
        assertEquals(3600, result.getBroadcastDuration());
    }

    @Test
    public void testIncrementViewsCount(){
        broadcastStatisticsMapper.incrementViewsCount(6L);
        BroadcastStatistics result = broadcastStatisticsMapper.findById(6L);
        assertEquals(1, result.getViewsCount());
    }

    @Test
    public void testGetStatisticsByVendorAndDateRange() {
        long vendorSeq = 1L;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse("2023-01-01 00:00:00", formatter);
        LocalDateTime endDate = LocalDateTime.parse("2024-12-31 23:59:59", formatter);

        List<BroadcastStatistics> statistics = broadcastStatisticsMapper.getStatisticsByVendorAndDateRange(vendorSeq, startDate, endDate);

        assertNotNull(statistics);
        assertEquals(4, statistics.size());
    }

    @Test
    public void testGetAverageBroadcastDuration() {
        long vendorSeq = 1L;
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-12-31");
        int avgBroadcastDuration = broadcastStatisticsMapper.getAverageBroadcastDuration(vendorSeq, startDate, endDate);
        assertTrue(avgBroadcastDuration > 0);
    }

    @Test
    public void testGetAverageViewerCount() {
        long vendorSeq = 1L;
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-12-31");
        int avgViewerCount = broadcastStatisticsMapper.getAverageViewerCount(vendorSeq, startDate, endDate);
        assertTrue(avgViewerCount > 0);
    }

    @Test
    public void testGetAveragePurchaseQuantity() {
        long vendorSeq = 1L;
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-12-31");
        int avgPurchaseQuantity = broadcastStatisticsMapper.getAveragePurchaseQuantity(vendorSeq, startDate, endDate);
        assertTrue(avgPurchaseQuantity > 0);
    }

    @Test
    public void testGetAverageProductClicks() {
        long vendorSeq = 1L;
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-12-31");
        int avgProductClicks = broadcastStatisticsMapper.getAverageProductClicks(vendorSeq, startDate, endDate);
        assertTrue(avgProductClicks > 0);
    }

    @Test
    public void testGetAverageViewingTime() {
        long vendorSeq = 1L;
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-12-31");
        int avgViewingTime = broadcastStatisticsMapper.getAverageViewingTime(vendorSeq, startDate, endDate);
        assertTrue(avgViewingTime > 0);
    }

    @Test
    public void testGetAverageLikesCount() {
        long vendorSeq = 1L;
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-12-31");
        int avgLikesCount = broadcastStatisticsMapper.getAverageLikesCount(vendorSeq, startDate, endDate);
        assertTrue(avgLikesCount > 0);
    }

    @Test
    public void testGetAveragePurchaseAmount() {
        long vendorSeq = 1L;
        LocalDate startDate = LocalDate.parse("2023-01-01");
        LocalDate endDate = LocalDate.parse("2023-12-31");
        long avgPurchaseAmount = broadcastStatisticsMapper.getAveragePurchaseAmount(vendorSeq, startDate, endDate);
        assertTrue(avgPurchaseAmount > 0);
    }


}
