package susussg.pengreenlive.order.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class ReviewDTO {

  private long orderSeq;
  private String userUUID;
  private long productSeq;
  private LocalDateTime orderDate;
  private int orderProductPrice;
  private String deliveryStatus;
  private boolean reviewYn;
  private String productNm;
  private String productImage;
  private String reviewContent;
  private LocalDateTime reviewTime;



}
