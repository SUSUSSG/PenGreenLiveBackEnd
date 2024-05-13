package susussg.pengreenlive.order.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.order.domain.Order;
import susussg.pengreenlive.order.dto.OrderFormDTO;
import susussg.pengreenlive.order.OrderRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Long persistOrder(OrderFormDTO orderForm) {

        Order order = modelMapper.map(orderForm, Order.class);
        log.info("OrderService {}", order);

        orderRepository.save(order);
        return order.getId();
    }
}
