package susussg.pengreenlive.payment.dto;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInitiateRequestDTO {
    private String orderId;
    private int amount;
}
