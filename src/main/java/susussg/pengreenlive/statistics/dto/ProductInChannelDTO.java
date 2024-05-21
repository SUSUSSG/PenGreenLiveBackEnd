package susussg.pengreenlive.statistics.dto;

import lombok.Data;

@Data
public class ProductInChannelDTO {
    private Long productSeq;
    private String productNm;
    private String productCd;
    private int totalSales;
}
