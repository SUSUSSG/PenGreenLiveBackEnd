package susussg.pengreenlive.broadcast.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LiveBroadcastInfoDTO {
    //방송
    private BroadcastDTO broadcast;

    //공지
    private List<NoticeDTO> notices;

    //질문과 답변
    private List<FaqDTO> faqs;

    //라이브 혜택
    private List<BenefitDTO> benefits;
}
