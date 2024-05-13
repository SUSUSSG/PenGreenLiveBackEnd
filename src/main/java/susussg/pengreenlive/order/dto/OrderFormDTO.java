package susussg.pengreenlive.order.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

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
    boolean reivewYn;
    long vendorSeq;
    long channelSeq;
}
