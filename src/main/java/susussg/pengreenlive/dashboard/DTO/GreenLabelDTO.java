package susussg.pengreenlive.dashboard.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GreenLabelDTO {

  private long productSeq;
  private long labelIdSeq;
  private String certificationReason;
  private LocalDateTime certificationExpirationDate;

}
