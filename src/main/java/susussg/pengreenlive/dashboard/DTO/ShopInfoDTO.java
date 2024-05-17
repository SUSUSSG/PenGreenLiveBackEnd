package susussg.pengreenlive.dashboard.DTO;

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
  private String channelImage;
  private String rawchannelImage;
  private String channelInfo;

}
