package susussg.pengreenlive.order.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_PRODUCT")
public class Product {

    @Id
    @Column(name = "PRODUCT_SEQ")
    private Long productSeq;

    @Column(name = "PRODUCT_CD")
    private String productCd;

    @Column(name = "GREEN_PRODUCT_ID")
    private String greenProductId;

    @Column(name = "PRODUCT_NM")
    private String productNm;

    @Column(name = "LIST_PRICE")
    private int listPrice;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "PRODUCT_IMAGE")
    private String productImage;

    @Column(name = "CATEGORY_CD")
    private String categoryCd;
}
