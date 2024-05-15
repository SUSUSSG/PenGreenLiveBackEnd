package susussg.pengreenlive.order.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class OrderFormDTO {
    long orderSeq;
    String userUUID;
    long productSeq;
    int orderQty;
    String orderPayment;
    LocalDateTime orderDate;
    int orderProductPrice;
    int orderPayedPrice;
    long broadcastSeq;
    String deliveryStatus;
    boolean reviewYn;
    long vendorSeq;
    long channelSeq;
}
