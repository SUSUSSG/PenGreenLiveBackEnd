package susussg.pengreenlive.broadcast.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.*;
import susussg.pengreenlive.broadcast.service.LiveRegisterService;

import java.io.IOException;
import java.util.List;

@RestController
@Log4j2
public class LiveRegisterController {

    @Autowired
    private final LiveRegisterService liveRegisterService;

    public LiveRegisterController(LiveRegisterService liveRegisterService) {
        this.liveRegisterService = liveRegisterService;
    }

    //판매자 고유번호
    long vendorId = 2;


    // 방송 등록
    @PostMapping("/live-register")
    public ResponseEntity<Void> registerBroadcast(@RequestBody BroadcastRegistrationRequest request) throws IOException {
        String channelName = liveRegisterService.getChannelName(vendorId);

        BroadcastDTO broadcastDTO = BroadcastDTO.builder()
                .channelNm(channelName)
                .broadcastTitle(request.getBroadcastTitle())
                .broadcastImage(request.getBroadcastImage().getBytes())
                .broadcastSummary(request.getBroadcastSummary())
                .broadcastScheduledTime(request.getBroadcastScheduledTime())
                .categoryCd(request.getCategoryCd())
                .build();

        BroadcastProductDTO broadcastProductDTO = BroadcastProductDTO.builder()
                .broadcastSeq(request.getBroadcastSeq())
                .productSeq(request.getProductSeq())
                .discountRate(request.getDiscountRate())
                .discountPrice(request.getDiscountPrice())
                .build();

        NoticeDTO noticeDTO = NoticeDTO.builder()
                .broadcastSeq(request.getBroadcastSeq())
                .noticeContent(request.getNoticeContent())
                .build();

        BenefitDTO benefitDTO = BenefitDTO.builder()
                .broadcastSeq(request.getBroadcastSeq())
                .benefitContent(request.getBenefitContent())
                .build();

        FaqDTO faqDTO = FaqDTO.builder()
                .broadcastSeq(request.getBroadcastSeq())
                .questionTitle(request.getQuestionTitle())
                .questionAnswer(request.getQuestionAnswer())
                .build();

        liveRegisterService.saveBroadcast(broadcastDTO);
        liveRegisterService.saveBroadcastProduct(broadcastProductDTO);
        liveRegisterService.saveNotice(noticeDTO);
        liveRegisterService.saveBenefit(benefitDTO);
        liveRegisterService.saveFaq(faqDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //채널별 상품 목록
    @GetMapping("/channel-sales-product")
    public ResponseEntity<List<ChannelSalesProductDTO>> showProduct() {
        List<ChannelSalesProductDTO> channelSalesProducts = liveRegisterService.getChannelSalesProductAll(vendorId);
        return ResponseEntity.ok().body(channelSalesProducts);
    }

}
