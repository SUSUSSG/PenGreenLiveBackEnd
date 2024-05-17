package susussg.pengreenlive.statistics.dto;

import lombok.Data;

@Data
public class SalesDataDTO {
    private Integer totalSales;
    private Integer totalOrders;
    private Integer avgUnitPrice;
    private Integer avgBuyersPerProduct;
    private Integer avgQuantityPerProduct;
}
