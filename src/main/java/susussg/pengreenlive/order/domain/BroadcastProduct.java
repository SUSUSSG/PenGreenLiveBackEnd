package susussg.pengreenlive.order.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TB_BROADCAST_PRODUCT")
public class BroadcastProduct {

    @Id
    @Column(name = "BROADCAST_SEQ")
    private Long broadcastSeq;

    @Column(name="PRODUCT_SEQ")
    private Long productSeq;
}
