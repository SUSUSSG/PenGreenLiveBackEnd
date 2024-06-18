package susussg.pengreenlive.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import susussg.pengreenlive.order.dto.BroadcastStockDTO;
import susussg.pengreenlive.order.repository.BroadcastRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BroadcastScheduler {

    private final BroadcastRepository broadcastRepository;
    private final ProductStockService productStockService;

    @Scheduled(cron = "0 0 0 * * *")
    public void loadInventoryForTomorrowBroadcasts() {
        LocalDate today = LocalDate.now();
        List<Long> broadcastSeqs  = broadcastRepository.findAllByBroadcastScheduledTime(today);
        productStockService.loadStockForBroadcasts(broadcastSeqs);
    }
}
