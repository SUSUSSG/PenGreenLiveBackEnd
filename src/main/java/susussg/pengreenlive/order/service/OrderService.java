package susussg.pengreenlive.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.order.domain.Order;
import susussg.pengreenlive.order.domain.ProductStock;
import susussg.pengreenlive.order.dto.OrderFormDTO;
import susussg.pengreenlive.order.repository.OrderRepository;
import susussg.pengreenlive.order.repository.ProductStockRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductStockRepository productStockRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Long persistOrder(OrderFormDTO orderForm) {

//        ProductStock productStock = productStockRepository.findByProduct_ProductSeq(orderForm.getProductSeq())
//                .orElseThrow(() -> new RuntimeException("Product stock not found"));
//
//        if (productStock.getProductStock() < orderForm.getOrderQty()) {
//            throw new RuntimeException("Not enough stock available");
//        }
//
//        productStock.setProductStock(productStock.getProductStock() - orderForm.getOrderQty());
//        productStockRepository.save(productStock);

        Order order = modelMapper.map(orderForm, Order.class);
        order.setReviewYn(false);
        order.setDeliveryStatus("결제완료");
        log.info("OrderService {}", order);

        orderRepository.save(order);

        return order.getId();
    }
}
