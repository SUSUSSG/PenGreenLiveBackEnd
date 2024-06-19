package susussg.pengreenlive.order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
    public void testReserveStock() throws InterruptedException {
        int numberOfUsers = 120;
        AtomicInteger successfulReservations = new AtomicInteger(0);
        AtomicInteger failedReservations = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(numberOfUsers);
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfUsers);

        for (int i = 0; i < numberOfUsers; i++) {
            final int userId = i;
            executorService.execute(() -> {
                try {
                    boolean result = productStockService.reserveStock(1L, 1L, "test-user-" + userId, 1);
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

        System.out.println("성공 예약 수: " + successfulReservations.get());
        System.out.println("실패 예약 수: " + failedReservations.get());

        executorService.shutdown();
    }
}
