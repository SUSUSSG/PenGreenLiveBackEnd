package susussg.pengreenlive.main.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MainCarouselProductDTO {
    private byte[] productImage;
    private String productNm;
    private int discountPrice;
}
