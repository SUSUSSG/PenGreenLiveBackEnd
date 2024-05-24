package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveProductStatsDTO {
    private long productSeq;
    private int remainingStock;
    private int totalOrders;
    private int totalOrderAmount;
}
