package susussg.pengreenlive.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.order.domain.Order;
import susussg.pengreenlive.order.dto.OrderFormDTO;
import susussg.pengreenlive.order.repository.OrderRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Long saveOrder(OrderFormDTO orderForm) {
        Order order = modelMapper.map(orderForm, Order.class);
        order.setReviewYn(false);
        order.setDeliveryStatus("결제완료");

        log.info("OrderService Order: {}", order);
        orderRepository.save(order);
        return order.getId();
    }
}
