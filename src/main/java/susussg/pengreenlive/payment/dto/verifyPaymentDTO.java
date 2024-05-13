package susussg.pengreenlive.payment.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class verifyPaymentDTO {
    private String orderId;
    private int amount;
}
