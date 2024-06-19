package susussg.pengreenlive.order.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductStockServiceTest {

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplateJson;

    @BeforeEach
    public void setUp() {
        String redisKey = "stock:broadcast:1";
        redisTemplateJson.opsForHash().put(redisKey, "1", 100);
    }

    @Test
    public void 재고예약_동시성() throws InterruptedException {
        int numberOfUsers = 120;
        AtomicInteger successfulReservations = new AtomicInteger(0);
        AtomicInteger failedReservations = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(numberOfUsers);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfUsers);

        for (int i = 0; i < numberOfUsers; i++) {
            final int userId = i;
            executorService.execute(() -> {
                try {
                    boolean result = productStockService.reserveStock(1L, 1L, "test-user-" + userId, 2);
                    if (result) {
                        successfulReservations.incrementAndGet();
                    } else {
                        failedReservations.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        log.info("성공 예약 수: {}" + successfulReservations.get());
        log.info("실패 예약 수: {}" + failedReservations.get());

        executorService.shutdown();
    }

    @Test
    public void 재고해제() {
        Long broadcastSeq = 1L;
        Long productSeq = 1L;
        String userUUID = "test-user-23";

        HashOperations<String, String, Integer> hashOps = redisTemplateJson.opsForHash();
        String reservedKey = "stock:reserved:" + productSeq;
        String redisKey = "stock:broadcast:" + broadcastSeq;
        hashOps.put(reservedKey, userUUID, 5);
        productStockService.releaseReservedStock(broadcastSeq, productSeq, userUUID);

        Integer reservedStock = hashOps.get(reservedKey, userUUID);
        Integer availableStock = hashOps.get(redisKey, productSeq.toString());

        log.info("예약 내역: {}", reservedStock);
        log.info("가용 재고: {}", availableStock);
    }

    @Test
    public void 재고확정() {
        Long productSeq = 1L;
        String userUUID = "test-user-23";
        productStockService.confirmStock(productSeq, userUUID);

        HashOperations<String, String, Integer> hashOps = redisTemplateJson.opsForHash();
        String reservedKey = "stock:reserved:" + productSeq;
        Integer reservedStock = hashOps.get(reservedKey, userUUID);

        log.info("예약 내역: {}", reservedStock);
    }
}
