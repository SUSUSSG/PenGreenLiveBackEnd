package susussg.pengreenlive.statistics.dto;

import java.util.List;
import lombok.Data;

@Data
public class ProductDetailsDTO {

    private Long productSeq;
    private String productCd;
    private String productNm;
    private String greenProductId;
    private Integer listPrice;
    private String brand;
    private String productImage;
    private String categoryNm;
    private List<LabelInfo> labels;


}
