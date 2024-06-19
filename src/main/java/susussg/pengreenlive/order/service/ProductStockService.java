package susussg.pengreenlive.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    private static final String STOCK_KEY_PREFIX = "stock:broadcast:";

    public boolean isStockAvailable(Long broadcastSeq, Long productSeq, int quantity) {
        HashOperations<String, String, Integer> hashOps = redisTemplateJson.opsForHash();
        String redisKey = STOCK_KEY_PREFIX + broadcastSeq;
        Integer stock = hashOps.get(redisKey, productSeq.toString());
        if (stock == null) {
            stock = loadStockFromDatabase(broadcastSeq, productSeq);
        }
        return stock >= quantity;
    }

    @Transactional
    public void reduceStock(Long broadcastId, Long productId, int quantity) {
        HashOperations<String, String, Integer> hashOps = redisTemplateJson.opsForHash();
        String redisKey = STOCK_KEY_PREFIX + broadcastId;
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
        String redisKey = STOCK_KEY_PREFIX + broadcastId;
        redisTemplateJson.opsForHash().put(redisKey, productId.toString(), stock);
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
        HashOperations<String, String, Integer> hashOps = redisTemplateJson.opsForHash();

        for (Map.Entry<Long, List<ProductStockDTO>> entry : groupedStocks.entrySet()) {
            Long broadcastSeq = entry.getKey();
            List<ProductStockDTO> stockList = entry.getValue();
            String redisKey = STOCK_KEY_PREFIX + broadcastSeq;

            Map<String, Integer> stockMap = stockList.stream()
                    .collect(Collectors.toMap(
                            ps -> ps.getProductSeq().toString(),
                            ProductStockDTO::getProductStock
                    ));
            hashOps.putAll(redisKey, stockMap);
        }
    }

    public boolean reserveStock(Long broadcastSeq, Long productSeq, String userUUID, int quantity) {
        HashOperations<String, String, Integer> hashOps = redisTemplateJson.opsForHash();
        String stockKey = "stock:reserved:" + productSeq;
        String redisKey = STOCK_KEY_PREFIX + broadcastSeq;

        Integer availableStock = hashOps.get(redisKey, productSeq.toString());
        if (availableStock == null || availableStock < quantity) {
            return false;
        }

        Integer reservedStock = hashOps.get(stockKey, userUUID);
        if (reservedStock == null) {
            reservedStock = 0;
        }

        hashOps.put(redisKey, productSeq.toString(), availableStock + reservedStock);

        availableStock = hashOps.get(stockKey, productSeq.toString());
        if (availableStock == null || availableStock < quantity) {
            return false;
        }
        hashOps.put(redisKey, productSeq.toString(), availableStock - quantity);

        hashOps.put(stockKey, userUUID, quantity);
        redisTemplateJson.opsForValue().set(userUUID, stockKey, 10, TimeUnit.MINUTES);

        return true;
    }
}
