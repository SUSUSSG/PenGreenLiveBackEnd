package susussg.pengreenlive.openai.dto;

import lombok.Data;

@Data
public class RecentOrderDTO {
    private String productName;
    private String productImage;
    private String channelName;
    private String channelImage;
    private String channelUrl;
}
