package susussg.pengreenlive.broadcast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.broadcast.dto.BroadcastProductDTO;
import susussg.pengreenlive.broadcast.mapper.ProductClicksMapper;

import java.util.List;
import java.util.Map;

@Service
public class ProductClickServiceImpl implements ProductClickService{

    @Autowired
    private ProductClicksMapper productClickMapper;

//    @Override
//    public void addProductClick(Long broadcastSeq, Long productSeq) {
//        ProductClick productClick = ProductClick.builder()
//                .broadcastSeq(broadcastSeq)
//                .productSeq(productSeq)
//                .build();
//
//        productClickMapper.insertProductClick(productClick);
//    }

//    @Override
//    @Transactional
//    public void addProductClicksForBroadcast(Long broadcastSeq) {
//        List<BroadcastProductDTO> broadcastProducts = productClickMapper.getProductsByBroadcastSeq(broadcastSeq);
//
//        for (BroadcastProductDTO broadcastProduct : broadcastProducts) {
//            ProductClick productClick = new ProductClick();
//            productClick.setBroadcastSeq(broadcastProduct.getBroadcastSeq());
//            productClick.setProductSeq(broadcastProduct.getProductSeq());
//
//            productClickMapper.insertProductClick(productClick);
//        }
//    }

    @Override
    @Transactional
    public void incrementProductClick(Long broadcastSeq, Long productSeq) {
        productClickMapper.incrementClickCount(broadcastSeq, productSeq);
    }

    @Override
    @Transactional
    public void updateAverageProductClicks(Long broadcastSeq) {
        productClickMapper.updateAverageClicks(broadcastSeq);
    }

    @Override
    public void updateConversionRates(long broadcastSeq) {
        // 업데이트에 필요한 정보 가져오기
        List<Map<String, Object>> counts = productClickMapper.getOrderAndClickCounts(broadcastSeq);

        // 각 방송 상품의 구매전환률 업데이트
        for (Map<String, Object> count : counts) {
            long productSeq = (long) count.get("PRODUCT_SEQ");
            int orderCount = ((Number) count.get("ORDER_COUNT")).intValue();

            productClickMapper.updateProductConversionRate(broadcastSeq, productSeq, orderCount);
        }

        // 방송 통계 테이블 구매전환률 업데이트
        productClickMapper.updateBroadcastConversionRate(broadcastSeq);
    }
}
