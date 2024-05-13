package susussg.pengreenlive.order.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_ORDER")
public class Order {

    @Id @GeneratedValue
    @Column(name="order_seq")
    long id;

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
