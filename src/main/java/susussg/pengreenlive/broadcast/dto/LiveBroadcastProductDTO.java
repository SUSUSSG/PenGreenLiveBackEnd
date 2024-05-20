package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveBroadcastProductDTO {
    private long productSeq;
    private String productImage;
    private String productNm;
    private String productCd;
    private int listPrice;
    private int discountRate;
    private int discountPrice;
    private String labelImages;
    private int productStock;
}
