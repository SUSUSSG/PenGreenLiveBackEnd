package susussg.pengreenlive.dashboard.DTO;

import java.time.LocalDateTime;
import java.util.List;
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
  private String greenProductId;
  private String productNm;
  private int listPrice;
  private String brand;
  private String base64Image;
  private byte[] productImage;
  private String categoryCd;
  private String categoryNm;
  private int productStock;
  private Long vendorSeq;
  private Long channelSeq;

  private long labelIdSeq;
  private String certificationReason;
  private String certificationExpirationDate;
  private List<GreenLabelDTO> labels;


}
