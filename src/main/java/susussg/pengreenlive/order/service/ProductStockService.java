//package susussg.pengreenlive.order.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import susussg.pengreenlive.order.domain.ProductStock;
//import susussg.pengreenlive.order.repository.ProductStockRepository;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//public class ProductStockService {
//
//    @Autowired
//    private ProductStockRepository productStockRepository;
//
//    @Autowired
//    private RedisTemplate<String, Integer> redisTemplate;
//    private static final String INVENTORY_KEY_PREFIX = "inventory:";
//
//    public boolean isStockAvailable(Long productId, int quantity) {
//        Integer stock = redisTemplate.opsForValue().get(INVENTORY_KEY_PREFIX + productId);
//        if (stock == null) {
//            stock = loadStockFromDatabase(productId);
//        }
//        return stock >= quantity;
//    }
//
//    @Transactional
//    public void reduceStock(Long productId, int quantity) {
//        Long stock = redisTemplate.opsForValue().decrement(INVENTORY_KEY_PREFIX + productId, quantity);
//        if (stock == null || stock < 0) {
//            redisTemplate.opsForValue().increment(INVENTORY_KEY_PREFIX + productId, quantity);
//            throw new RuntimeException("Not enough stock available");
//        }
//        updateStockInDatabase(productId, quantity);
//    }
//
//    private Integer loadStockFromDatabase(Long productId) {
//        ProductStock productStock = productStockRepository.findByProduct_ProductSeq(productId)
//                .orElseThrow(() -> new RuntimeException("Product stock not found"));
//        int stock = productStock.getProductStock();
//        redisTemplate.opsForValue().set(INVENTORY_KEY_PREFIX + productId, stock);
//        return stock;
//    }
//
//    private void updateStockInDatabase(Long productId, int quantity) {
//        ProductStock productStock = productStockRepository.findByProduct_ProductSeq(productId)
//                .orElseThrow(() -> new RuntimeException("Product stock not found"));
//        productStock.setProductStock(productStock.getProductStock() - quantity);
//        productStockRepository.save(productStock);
//    }
//
//    public void loadStockForBroadcast(long broadcastId) {
//        List<ProductStock> stocks = productStockRepository.findAllByBroadcastId(broadcastId);
//        for (ProductStock stock : stocks) {
//            redisTemplate.opsForValue().set(INVENTORY_KEY_PREFIX + stock.getProduct().getProductSeq(), stock.getProductStock());
//        }
//    }
//}
