package susussg.pengreenlive.dashboard.DTO;

import java.sql.Blob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopInfoDTO {

  private Long channelSeq;
  private String channelNM;
  private String channelUrl;
  private byte[] channelImage;
  private String channelInfo;

}
