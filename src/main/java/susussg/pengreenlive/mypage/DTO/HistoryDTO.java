package susussg.pengreenlive.mypage.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {

  private String userUUID;
  private long broadcastSeq;
  private String broadcastTitle;
  private String broadcastImage;
  private long productSeq;
  private String productNm;
  private String productImage;
  private int listPrice;
  private String brand;

}
