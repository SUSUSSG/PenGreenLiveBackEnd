package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BroadcastViewerCount {
    private Long id;
    private Long broadcastSeq;
    private LocalDateTime recordTime;
    private Integer viewerCount;
}
