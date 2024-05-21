package susussg.pengreenlive.broadcast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.broadcast.mapper.ProductClicksMapper;

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
}
