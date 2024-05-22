package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BroadcastTimeDTO {
    private long broadcastSeq;
    private LocalDateTime time;
    private String action;
}
