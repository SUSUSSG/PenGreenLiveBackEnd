package susussg.pengreenlive.order.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BroadcastStockDTO {
    private long broadcastSeq;
    private LocalDateTime broadcastScheduledTime;
}
