package susussg.pengreenlive.main.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveChanceCarouselDTO {

    private int broadcastSeq;
    private byte[] broadcastImage;
    private String broadcastTitle;
    private byte[] productImage;
    private String productNm;
    private int discountPrice;
    private int discountRate;
}
