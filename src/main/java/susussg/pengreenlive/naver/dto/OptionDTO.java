package susussg.pengreenlive.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDTO {

  private String language;
  private String model;
  private int tone;
  private int summaryCount;

}
