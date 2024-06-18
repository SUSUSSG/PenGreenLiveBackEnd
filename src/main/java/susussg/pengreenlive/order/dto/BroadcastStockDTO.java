package susussg.pengreenlive.order.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BroadcastStockDTO {
    private Long broadcastSeq;
    private LocalDateTime broadcastScheduledTime;
}
