//package susussg.pengreenlive.order.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import susussg.pengreenlive.order.dto.BroadcastStockDTO;
//import susussg.pengreenlive.order.repository.BroadcastRepository;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Component
//public class BroadcastScheduler {
//
//    private final BroadcastRepository broadcastRepository;
//    private final ProductStockService productStockService;
//
//    @Scheduled(cron = "0 0 0 * * *")  // 매일 자정 실행
//    public void loadInventoryForTomorrowBroadcasts() {
//        LocalDateTime tomorrowStart = LocalDateTime.now().plus(1, ChronoUnit.DAYS).toLocalDate().atStartOfDay();
//        LocalDateTime tomorrowEnd = tomorrowStart.plusDays(1).minusSeconds(1);
//
//        List<BroadcastStockDTO> broadcasts = broadcastRepository.findAllByBroadcastScheduledTime(tomorrowStart, tomorrowEnd);
//        for (BroadcastStockDTO broadcast : broadcasts) {
//            productStockService.loadStockForBroadcast(broadcast.getBroadcastSeq());
//        }
//    }
//}
