package susussg.pengreenlive.order.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReviewDTO {

  long orderSeq;
  String userUUID;
  long productSeq;
  LocalDateTime orderDate;
  int orderProductPrice;
  String deliveryStatus;
  boolean reviewYn;
  private String productNm;
  private byte[] productImage;


}
