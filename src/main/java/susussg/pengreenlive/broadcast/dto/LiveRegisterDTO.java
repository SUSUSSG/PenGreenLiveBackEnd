package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveRegisterDTO {
    //방송
    private int broadcastSeq;
    private String channelNm;
    private String broadcastTitle;
    private Blob broadcastImage;
    private String broadcastSummary;
    private Date broadcastScheduledTime;
    private String categoryCd;

    //방송판매상품
    private int productSeq;
    private int discountRate;
    private int discountPrice;

    //공지사항
    private int noticeSeq;
    private String noticeContent;
    //라이브혜택
    private int benefitSeq;
    private String benefitContent;
    //자주묻는질문
    private int faqSeq;
    private String questionTitle;
    private String questionAnswer;

}
