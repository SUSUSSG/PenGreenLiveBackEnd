package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BroadcastDTO {
    //방송
    private long broadcastSeq;
    private String vendorSeq;
    private String channelNm;
    private String broadcastTitle;
    private String broadcastImage;
    private String broadcastSummary;
    private LocalDateTime broadcastScheduledTime;
    private String categoryCd;
}
