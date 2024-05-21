package susussg.pengreenlive.order.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import susussg.pengreenlive.order.dto.OrderFormDTO;
import susussg.pengreenlive.util.DTO.BanwordValidationResultDTO;
import susussg.pengreenlive.util.Service.BanwordService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log4j2
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testOrderInsert() {
        OrderFormDTO orderForm = OrderFormDTO.builder()
                .userUUID("f23a72e0-1347-11ef-b085-f220affc9a21")
                .productSeq(3)
                .orderQty(1)
                .orderPayment("카드")
                .orderDate(LocalDateTime.parse("2024-05-20T10:04:50"))
                .orderProductPrice(7000)
                .orderPayedPrice(7000)
                .broadcastSeq(2)
                .deliveryStatus("결제완료")
                .reviewYn(false)
                .vendorSeq(0)
                .channelSeq(0).build();

        log.info("order form {}", orderForm);

        long orderId = orderService.persistOrder(orderForm);
        log.info("order Id = {}", orderId);
    }
}