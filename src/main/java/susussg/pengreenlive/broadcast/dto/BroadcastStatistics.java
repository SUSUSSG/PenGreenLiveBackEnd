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
    long broadcastSeq;
    int avgViewerCount;
    int maxViewerCount;
    int likesCount;
    int viewsCount;
    long avgPurchaseAmount;
    int avgViewingTime;
    int broadcastDuration;
    int avgProductClicks;
    long totalSalesAmount;
    int totalSalesQty;
}
