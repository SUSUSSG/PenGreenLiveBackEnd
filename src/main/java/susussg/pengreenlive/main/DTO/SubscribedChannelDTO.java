package susussg.pengreenlive.main.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribedChannelDTO {
    private Long channelSeq;
    private String channelNm;
    private String channelUrl;
    private String channelImage;
    private String channelInfo;
}
