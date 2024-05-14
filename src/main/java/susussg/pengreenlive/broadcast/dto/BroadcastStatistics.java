package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastStatistics {
    long broadcastSeq;
    int avgViewerCount;
    int maxViewerCount;
    int likesCount;
    long avgPurchaseAmount;
    int avgViewingTime;
    int broadcastDuration;
    int avgProductClicks;
    long totalSalesAmount;
    int totalSalesQty;
}
