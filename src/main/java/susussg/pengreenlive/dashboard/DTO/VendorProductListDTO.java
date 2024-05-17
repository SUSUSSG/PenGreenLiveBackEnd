package susussg.pengreenlive.dashboard.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorProductListDTO {

  private Long productSeq;
  private String productCd;
  private String greenProductId;
  private String productNm;
  private int listPrice;
  private String brand;
  private byte[] base64Image;
  private String productImage;
  private String categoryCd;
  private String categoryNm;
  private int productStock;
  private Long venderSeq;
  private Long channelSeq;

}
