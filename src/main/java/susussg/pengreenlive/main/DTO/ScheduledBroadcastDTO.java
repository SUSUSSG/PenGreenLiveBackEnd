package susussg.pengreenlive.main.DTO;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ScheduledBroadcastDTO {
    private int broadcastSeq;
    private LocalDateTime broadcastScheduledTime;
    private String broadcastTitle;
    private String broadcastImage;
    private String broadcastSummary;
    private String benefitContent;
    private int discountPrice;
    private int discountRate;
    private int listPrice;
    private String channelImage;
    private String channelNm;
    private int channelSeq;
    private String productNm;
    private String productImage;
}
