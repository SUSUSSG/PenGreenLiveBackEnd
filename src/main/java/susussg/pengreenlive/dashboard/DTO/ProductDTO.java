package susussg.pengreenlive.dashboard.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

  private Long productSeq;
  private String productCd;
  private String greenProductCd;
  private String productNm;
  private int listPrice;
  private String brand;
  private String productImage;
  private String categoryCd;
  private String productStock;

}
