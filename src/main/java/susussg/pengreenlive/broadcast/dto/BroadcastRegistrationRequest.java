package susussg.pengreenlive.broadcast.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class BroadcastRegistrationRequest {
    private String broadcastTitle;
    private MultipartFile broadcastImage;
    private String broadcastSummary;
    private Date broadcastScheduledTime;
    private String categoryCd; // 상품테이블 pk 가져와야 함
    private long productSeq;
    private int discountRate;
    private int discountPrice;
    private String noticeContent;
    private String benefitContent;
    private String questionTitle;
    private String questionAnswer;
}
