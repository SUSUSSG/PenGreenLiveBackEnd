package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingBroadcastInfoDTO {
    //방송
    private long broadcastSeq;
    private String broadcastTitle;
    private byte[] broadcastImage;
    private Date broadcastScheduledTime;
    //상품
    private long productSeq;
    private String productNm;
    private byte[] productImage;
    private int listPrice;
    //방송 판매 상품
    private int discountRate;
    private int discountPrice;


}
