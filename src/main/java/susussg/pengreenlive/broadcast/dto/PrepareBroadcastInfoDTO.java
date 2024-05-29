package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrepareBroadcastInfoDTO {
    //방송
    private long broadcastSeq;
    private String broadcastTitle;
    private String broadcastImage;
    private LocalDateTime broadcastScheduledTime;
    //상품
    private List<LiveBroadcastProductDTO> productList;


}
