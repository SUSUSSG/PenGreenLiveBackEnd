package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductClick {
    private Long clicksSeq;
    private Long broadcastSeq;
    private Long productSeq;
    private Long clicksCount;
}
