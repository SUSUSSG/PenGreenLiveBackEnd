package susussg.pengreenlive.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import susussg.pengreenlive.order.domain.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockDTO {
    private Long productSeq;
    private Long broadcastSeq;
    private int productStock;

}