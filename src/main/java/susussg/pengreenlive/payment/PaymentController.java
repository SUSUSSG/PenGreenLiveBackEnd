package susussg.pengreenlive.payment;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.payment.dto.PaymentInitiateRequestDTO;
import susussg.pengreenlive.payment.service.PaymentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 금액 검증
    @GetMapping("/verify/{orderId}")
    public ResponseEntity<?> getPaymentInfo(@PathVariable("orderId") String orderId, HttpSession session) {
        try {
            Object amount = session.getAttribute(orderId);
            System.out.println(amount);
            return ResponseEntity.ok().body("Payment initiation successful.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error initiating payment.");
        }
    }
    @PostMapping("/verify")
    public ResponseEntity<?> initiatePayment(@RequestBody PaymentInitiateRequestDTO paymentRequest, HttpSession session) {
        try {
            paymentService.savePaymentDetails(paymentRequest.getOrderId(), paymentRequest.getAmount());
            session.setAttribute(paymentRequest.getOrderId(), paymentRequest.getAmount());
            return ResponseEntity.ok().body("Payment initiation successful.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error initiating payment.");
        }
    }



    // 토스 결제 요청 및 처리

    // 결제 결과에 따른 응답 반환

    public void setOrderDetail() {

    }
}
