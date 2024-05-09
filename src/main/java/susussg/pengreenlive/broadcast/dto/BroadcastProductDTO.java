package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BroadcastProductDTO {
    private int broadcastSeq;
    private int productSeq;
    private int discountRate;
    private int discountPrice;
}
