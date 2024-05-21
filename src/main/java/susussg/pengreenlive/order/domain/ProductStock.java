package susussg.pengreenlive.order.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_PRODUCT_STOCK")
public class ProductStock {

    @Id
    @OneToOne
    @JoinColumn(name = "PRODUCT_SEQ")
    private Product product;

    @Column(name="PRODUCT_STOCK")
    private int productStock;
}
