package susussg.pengreenlive.order.domain;

import jakarta.persistence.*;
import lombok.*;
import susussg.pengreenlive.order.util.UUIDConverter;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_SEQ")
    private long id;

    @Convert(converter = UUIDConverter.class)
    @Column(name = "USER_UUID", columnDefinition = "BINARY(16)")
    private String userUuid;

    @OneToOne(optional = true)
    @JoinColumn(name = "PRODUCT_SEQ", referencedColumnName = "PRODUCT_SEQ")
    private Product product;

    @Column(name = "ORDER_QTY")
    private int orderQty;

    @Column(name = "ORDER_PAYMENT")
    private String orderPayment;

    @Column(name = "ORDER_DATE")
    private LocalDateTime orderDate;

    @Column(name = "ORDER_PRODUCT_PRICE")
    private int orderProductPrice;

    @Column(name = "ORDER_PAYED_PRICE")
    private int orderPayedPrice;

    @OneToOne
    @JoinColumn(name="BROADCAST_SEQ")
    private Broadcast broadcast;

    @Column(name = "DELIVERY_STATUS")
    private String deliveryStatus;

    @Column(name = "REVIEW_YN")
    private boolean reviewYn;

    @Column(name = "VENDOR_SEQ")
    private long vendorSeq;

    @OneToOne
    @JoinColumn(name = "CHANNEL_SEQ")
    private Channel channel;
}
