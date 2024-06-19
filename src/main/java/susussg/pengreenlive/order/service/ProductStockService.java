package susussg.pengreenlive.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.order.domain.ProductStock;
import susussg.pengreenlive.order.dto.ProductStockDTO;
import susussg.pengreenlive.order.repository.ProductStockRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductStockService {

    private final ProductStockRepository productStockRepository;
    private final RedisTemplate<String, Object> redisTemplateJson;
    private final ModelMapper modelMapper;

    @Autowired
    private RedissonClient redissonClient;

    private static final String STOCK_KEY_PREFIX = "stock:broadcast:";
    private static final String RESERVED_KEY_PREFIX = "stock:reserved:";
    private static final String LOCK_KEY_PREFIX = "lock:";
    private static final int LOCK_WAIT_TIME = 1; // seconds
    private static final int LOCK_LEASE_TIME = 3; // seconds

    private HashOperations<String, String, Integer> hashOps;

    @Autowired
    public void setHashOperations(RedisTemplate<String, Object> redisTemplateJson) {
        this.hashOps = redisTemplateJson.opsForHash();
    }

    public boolean isStockAvailable(Long broadcastSeq, Long productSeq, int quantity) {
        String redisKey = getStockKey(broadcastSeq);
        Integer stock = hashOps.get(redisKey, productSeq.toString());
        if (stock == null) {
            stock = loadStockFromDatabase(broadcastSeq, productSeq);
        }
        return stock >= quantity;
    }

    @Transactional
    public void reduceStock(Long broadcastId, Long productId, int quantity) {
        String redisKey = getStockKey(broadcastId);
        Integer stock = hashOps.get(redisKey, productId.toString());

        if (stock == null || stock < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        hashOps.put(redisKey, productId.toString(), stock - quantity);
        updateStockInDatabase(productId, quantity);
    }

    private Integer loadStockFromDatabase(Long broadcastId, Long productId) {
        ProductStockDTO productStock = productStockRepository.findByProduct_ProductSeq(productId)
                .orElseThrow(() -> new RuntimeException("Product stock not found"));
        int stock = productStock.getProductStock();
        String redisKey = getStockKey(broadcastId);
        hashOps.put(redisKey, productId.toString(), stock);
        return stock;
    }

    private void updateStockInDatabase(Long productId, int quantity) {
        ProductStockDTO productStockDTO = productStockRepository.findByProduct_ProductSeq(productId)
                .orElseThrow(() -> new RuntimeException("Product stock not found"));
        productStockDTO.setProductStock(productStockDTO.getProductStock() - quantity);
        ProductStock productStock = modelMapper.map(productStockDTO, ProductStock.class);
        productStockRepository.save(productStock);
    }

    public void loadStockForBroadcasts(List<Long> broadcastIds) {
        List<ProductStockDTO> stocks = productStockRepository.findAllByBroadcastSeqIn(broadcastIds);
        Map<Long, List<ProductStockDTO>> groupedStocks = stocks.stream()
                .collect(Collectors.groupingBy(ProductStockDTO::getBroadcastSeq));

        stocks.stream().forEach(stock-> log.info("stock {}", stock));

        for (Map.Entry<Long, List<ProductStockDTO>> entry : groupedStocks.entrySet()) {
            Long broadcastSeq = entry.getKey();
            List<ProductStockDTO> stockList = entry.getValue();
            String redisKey = getStockKey(broadcastSeq);

            Map<String, Integer> stockMap = stockList.stream()
                    .collect(Collectors.toMap(
                            ps -> ps.getProductSeq().toString(),
                            ProductStockDTO::getProductStock
                    ));
            hashOps.putAll(redisKey, stockMap);
        }
    }

    // 재고 예약
    public boolean reserveStock(Long broadcastSeq, Long productSeq, String userUUID, int quantity) {
        String stockKey = getReservedKey(productSeq);
        String redisKey = getStockKey(broadcastSeq);

        RLock lock = redissonClient.getLock(getLockKey(productSeq));
        try {
            if (lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
                try {
                    Integer availableStock = hashOps.get(redisKey, productSeq.toString());
                    if (availableStock == null || availableStock < quantity) {
                        return false;
                    }

                    Integer reservedStock = hashOps.get(stockKey, userUUID);
                    if (reservedStock == null) {
                        reservedStock = 0;
                    }

                    availableStock += reservedStock;
                    if (availableStock < quantity) {
                        return false;
                    }
                    availableStock -= quantity;

                    hashOps.put(redisKey, productSeq.toString(), availableStock);
                    hashOps.put(stockKey, userUUID, quantity);

                    return true;

                } finally {
                    lock.unlock();
                }
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    // 재고 해제
    public void releaseReservedStock(Long broadcastSeq, Long productSeq, String userUUID) {
        String reservedKey = getReservedKey(productSeq);
        String redisKey = getStockKey(broadcastSeq);
        RLock lock = redissonClient.getLock(getLockKey(productSeq));

        try {
            if (lock.tryLock(LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS)) {
                try {
                    Integer reservedQuantity = hashOps.get(reservedKey, userUUID);

                    if (reservedQuantity != null) {
                        Integer currentStock = hashOps.get(redisKey, productSeq.toString());
                        if (currentStock != null) {
                            hashOps.put(redisKey, productSeq.toString(), currentStock + reservedQuantity);
                        } else {
                            hashOps.put(redisKey, productSeq.toString(), reservedQuantity);
                        }
                        hashOps.delete(reservedKey, userUUID);
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                throw new RuntimeException("lock 획득 실패");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Lock interrupted", e);
        }
    }

    // 재고 확정
    public void confirmStock(Long productSeq, String userUUID) {
        String stockKey = getReservedKey(productSeq);

        hashOps.delete(stockKey, userUUID);
    }

    private String getStockKey(Long broadcastSeq) {
        return STOCK_KEY_PREFIX + broadcastSeq;
    }

    private String getReservedKey(Long productSeq) {
        return RESERVED_KEY_PREFIX + productSeq;
    }

    private String getLockKey(Long productSeq) {
        return LOCK_KEY_PREFIX + productSeq;
    }
}
