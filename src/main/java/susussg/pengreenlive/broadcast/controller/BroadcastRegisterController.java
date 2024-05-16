package susussg.pengreenlive.broadcast.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.*;
import susussg.pengreenlive.broadcast.service.BroadcastRegisterService;

import java.util.Base64;
import java.util.List;

@RestController
@Log4j2
public class BroadcastRegisterController {

    @Autowired
    private final BroadcastRegisterService broadcastRegisterService;

    public BroadcastRegisterController(BroadcastRegisterService broadcastRegisterService) {
        this.broadcastRegisterService = broadcastRegisterService;
    }

    //판매자 고유번호
    long vendorId = 2;

    // 방송 카테고리 목록 불러오기
    @GetMapping("/broadcast-category")
    public ResponseEntity<List<BroadcastCategoryDTO>> fetchBroadcastCategory() {
        List<BroadcastCategoryDTO> categoryList = broadcastRegisterService.getAllCategory();
        return ResponseEntity.ok().body(categoryList);
    }

    // 방송 등록
    @PostMapping(value = "/register-broadcast")
    public ResponseEntity<String> registerBroadcast(@RequestBody BroadcastRegistrationRequestDTO request) {
        String channelName = broadcastRegisterService.getChannelName(vendorId); // 판매자 정보

        BroadcastDTO broadcastDTO = BroadcastDTO.builder()
                .channelNm(channelName)
                .broadcastTitle(request.getBroadcastTitle())
                .broadcastSummary(request.getBroadcastSummary())
                .broadcastScheduledTime(request.getBroadcastScheduledTime())
                .categoryCd(request.getCategoryCd())
                .build();

        if (request.getImage() != null) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(request.getImage());
                broadcastDTO.setBroadcastImage(imageBytes);
            } catch (Exception e) {
                String errorMessage = "이미지 파일 처리 중 오류 발생: " + e.getMessage();
                return ResponseEntity.badRequest().body(errorMessage);
            }
        }

        broadcastRegisterService.saveBroadcast(broadcastDTO);

        request.getRegisteredProducts().forEach(productInfo -> {
            BroadcastProductDTO productDTO = BroadcastProductDTO.builder()
                    .broadcastSeq(broadcastDTO.getBroadcastSeq())
                    .productSeq(productInfo.getProductSeq())
                    .discountRate(productInfo.getDiscountRate())
                    .discountPrice(productInfo.getDiscountPrice())
                    .build();
            broadcastRegisterService.saveBroadcastProduct(productDTO);
        });

        request.getNotices().forEach(notice -> {
            NoticeDTO noticeDTO = NoticeDTO.builder()
                    .broadcastSeq(broadcastDTO.getBroadcastSeq())
                    .noticeContent(notice)
                    .build();
            broadcastRegisterService.saveNotice(noticeDTO);
        });

        request.getBenefits().forEach(benefit -> {
            BenefitDTO benefitDTO = BenefitDTO.builder()
                    .broadcastSeq(broadcastDTO.getBroadcastSeq())
                    .benefitContent(benefit)
                    .build();
            broadcastRegisterService.saveBenefit(benefitDTO);
        });

        request.getQa().forEach(qaInfo -> {
            FaqDTO faqDTO = FaqDTO.builder()
                    .broadcastSeq(broadcastDTO.getBroadcastSeq())
                    .questionTitle(qaInfo.getQuestionTitle())
                    .questionAnswer(qaInfo.getQuestionAnswer())
                    .build();
            broadcastRegisterService.saveFaq(faqDTO);
        });

        return ResponseEntity.ok().body("방송 정보가 성공적으로 등록되었습니다.");
    }


    //채널별 상품 목록
    @GetMapping("/channel-sales-product")
    public ResponseEntity<List<ChannelSalesProductDTO>> fetchChannelSalesProduct() {
        List<ChannelSalesProductDTO> channelSalesProducts = broadcastRegisterService.getChannelSalesProductAll(vendorId);
        return ResponseEntity.ok().body(channelSalesProducts);
    }

    // 방송 예정 목록
    @GetMapping("/upcoming-broadcasts")
    public List<UpcomingBroadcastInfoDTO> fetchUpcomingBroadcasts() {
        log.info("방송 준비 컨트롤러 호출");
        return broadcastRegisterService.getUpcomingBroadcastInfo(vendorId);
    }

}
