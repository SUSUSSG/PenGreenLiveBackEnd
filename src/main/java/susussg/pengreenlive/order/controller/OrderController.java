package susussg.pengreenlive.order.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.login.service.SecurityService;
import susussg.pengreenlive.order.dto.OrderFormDTO;
import susussg.pengreenlive.order.service.OrderService;
import susussg.pengreenlive.order.service.ProductStockService;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping ("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final SecurityService securityService;
    private final ProductStockService productStockService;

    @Operation(summary = "상품 주문", description = "주문한 내역을 저장합니다.")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderFormDTO orderForm) {
        String userUUID = securityService.getCurrentUserUuid();

        try {
            orderForm.setUserUUID(userUUID);
            Long orderSeq = orderService.saveOrder(orderForm);
            return ResponseEntity.ok().body(orderSeq);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("failed");
        }
    }

    @Operation(summary = "상품 재고 확인", description = "주문할 상품의 재고를 확인합니다.")
    @GetMapping("/product/{productSeq}/stock")
    public ResponseEntity<?> getProductStock(@RequestParam Long broadcastSeq,
                                             @PathVariable Long productSeq,
                                             @RequestParam int quantity) {
        
        Boolean result = productStockService.isStockAvailable(broadcastSeq, productSeq, quantity);

        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "재고 예약", description = "결제 페이지에 진입 시, 재고를 선점합니다.")
    @PatchMapping("/product/{productSeq}/stock")
    public ResponseEntity<?> holdProductStock(@PathVariable Long productSeq,
                                              @RequestBody Map<String, Integer> requestBody) {

        String userUUID = securityService.getCurrentUserUuid();
        Boolean result = productStockService.reserveStock(requestBody.get("broadcastSeq").longValue(),
                productSeq, userUUID, requestBody.get("quantity"));
        return ResponseEntity.ok().body(result);
    }
}
