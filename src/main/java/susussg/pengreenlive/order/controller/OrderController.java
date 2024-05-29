package susussg.pengreenlive.order.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.login.service.SecurityService;
import susussg.pengreenlive.order.dto.OrderFormDTO;
import susussg.pengreenlive.order.service.OrderService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping ("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final SecurityService securityService;
    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderFormDTO orderForm) {
        log.info("OrderController {}", orderForm);
        String userUUID = securityService.getCurrentUserUuid();

        try {
            orderForm.setUserUUID(userUUID);
            Long orderSeq = orderService.persistOrder(orderForm);
            return ResponseEntity.ok().body(orderSeq);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("order failed");
        }
    }
}
