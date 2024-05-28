package susussg.pengreenlive.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.order.domain.Order;
import susussg.pengreenlive.order.domain.ProductStock;
import susussg.pengreenlive.order.dto.OrderFormDTO;
import susussg.pengreenlive.order.repository.OrderRepository;
import susussg.pengreenlive.order.repository.ProductStockRepository;
import susussg.pengreenlive.payment.service.PaymentService;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
//    private final ProductStockRepository productStockRepository;
    private final ModelMapper modelMapper;

//    @Autowired
//    private ProductStockService productStockService;

    @Autowired
    private PaymentService paymentService;

//    @Transactional
//    public boolean processOrder(Long productId, int quantity, OrderFormDTO orderForm) {
//        if (productStockService.isStockAvailable(productId, quantity)) {
//            boolean paymentSuccessful = paymentService.processPayment(productId, quantity);
//            if (paymentSuccessful) {
//                productStockService.reduceStock(productId, quantity);
//                persistOrder(orderForm);  // 주문을 저장
//                return true;
//            }
//        }
//        return false;
//    }

    @Transactional
    public Long persistOrder(OrderFormDTO orderForm) {
        Order order = modelMapper.map(orderForm, Order.class);
        order.setReviewYn(false);
        order.setDeliveryStatus("결제완료");
        log.info("OrderService {}", order);

        orderRepository.save(order);
        return order.getId();
    }
}

