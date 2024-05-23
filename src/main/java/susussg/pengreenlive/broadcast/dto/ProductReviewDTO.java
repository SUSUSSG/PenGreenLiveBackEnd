package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDTO {

  private String reviewTime;
  private String reviewContent;
  private Long productSeq;
  private Long userUuid;
  private String userNm;


}
