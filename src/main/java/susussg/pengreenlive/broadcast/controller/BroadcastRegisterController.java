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
    public ResponseEntity<String> registerBroadcast(@RequestBody BroadcastRegistrationRequestDTO broadcastRegisterInfo) {
        broadcastRegisterService.registerBroadcast(broadcastRegisterInfo, vendorId);
        return ResponseEntity.ok().body("방송 정보가 성공적으로 등록되었습니다.");
    }


    //채널별 상품 목록
    @GetMapping("/channel-sales-product")
    public ResponseEntity<List<ChannelSalesProductDTO>> fetchChannelSalesProduct() {
        List<ChannelSalesProductDTO> channelSalesProducts = broadcastRegisterService.getChannelSalesProductAll(vendorId);
        return ResponseEntity.ok().body(channelSalesProducts);
    }

    // 방송 예정 목록
    @GetMapping("/prepare-broadcasts")
    public List<PrepareBroadcastInfoDTO> fetchUpcomingBroadcasts() {
        log.info("방송 준비 컨트롤러 호출");
        return broadcastRegisterService.getUpcomingBroadcastInfo(vendorId);
    }

}
