package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChannelSalesProductDTO {
    private long productSeq;
    private byte[] productImage;
    private String productCd;
    private String productNm;
    private int listPrice;
    private long vendorSeq;
}
