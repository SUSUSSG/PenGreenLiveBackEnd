package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FaqDTO {
    private long broadcastSeq;
    private long faqSeq;
    private String questionTitle;
    private String questionAnswer;
}
