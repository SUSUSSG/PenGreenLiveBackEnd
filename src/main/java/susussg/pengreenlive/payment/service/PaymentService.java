package susussg.pengreenlive.payment.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public void savePaymentDetails(String orderId, double amount) {
        System.out.println("Saving payment details for order ID: " + orderId + " with amount: " + amount);
    }
}
