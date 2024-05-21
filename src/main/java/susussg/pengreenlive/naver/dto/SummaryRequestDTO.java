package susussg.pengreenlive.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryRequestDTO {

  private DocumentDTO document;
  private OptionDTO option;

}
