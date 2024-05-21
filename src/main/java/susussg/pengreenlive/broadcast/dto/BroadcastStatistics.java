package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BroadcastStatistics {
    private long broadcastSeq;
    private int avgViewerCount;
    private int maxViewerCount;
    private int likesCount;
    private int viewsCount;
    private long avgPurchaseAmount;
    private int avgViewingTime;
    private int broadcastDuration;
    private int avgProductClicks;
    private long totalSalesAmount;
    private int totalSalesQty;
    private int conversionRate;
}
