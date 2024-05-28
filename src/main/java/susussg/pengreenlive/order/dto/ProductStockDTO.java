package susussg.pengreenlive.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import susussg.pengreenlive.order.domain.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockDTO {
    private Product Order;
    private int productStock;
}
