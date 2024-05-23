package susussg.pengreenlive.openai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiBroadcastPromptDTO {
    private String channelNm;
    private String broadcastTitle;
    private String broadcastSummary;
    private String benefitContent;
    private String noticeContent;
    private String discountRate;
    private String discountPrice;
    private String questionTitle;
    private String questionAnswer;
    private String productNm;
    private Integer listPrice;
    private String brand;
    private String productImage;
    private String certificationReason;
    private String labelNm;
    private String labelDescription;
}