package susussg.pengreenlive.broadcast.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.*;
import susussg.pengreenlive.broadcast.service.BroadcastService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@Log4j2
public class BroadcastController {

    @Autowired
    private final BroadcastService broadcastService;

    public BroadcastController(BroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    //판매자 고유번호
    long vendorId = 3;
    // 방송 카테고리 목록 불러오기
    @GetMapping("/broadcast-category")
    public ResponseEntity<List<BroadcastCategoryDTO>> fetchBroadcastCategory() {
        List<BroadcastCategoryDTO> categoryList = broadcastService.getAllCategory();
        return ResponseEntity.ok().body(categoryList);
    }

    // 방송 등록
    @PostMapping(value = "/register-broadcast")
    public ResponseEntity<String> registerBroadcast(@RequestBody BroadcastRegistrationRequestDTO broadcastRegisterInfo) {
        broadcastService.registerBroadcast(broadcastRegisterInfo, vendorId);
        return ResponseEntity.ok().body("방송 정보가 성공적으로 등록되었습니다.");
    }


    //채널별 상품 목록
    @GetMapping("/channel-sales-product")
    public ResponseEntity<List<ChannelSalesProductDTO>> fetchChannelSalesProduct() {
        List<ChannelSalesProductDTO> channelSalesProducts = broadcastService.getChannelSalesProductAll(vendorId);
        return ResponseEntity.ok().body(channelSalesProducts);
    }

    // 방송 예정 목록
    @GetMapping("/prepare-broadcasts")
    public List<PrepareBroadcastInfoDTO> fetchUpcomingBroadcasts() {
        log.info("방송 준비 컨트롤러 호출");
        return broadcastService.getUpcomingBroadcastInfo(vendorId);
    }

    @GetMapping("/vendor/{vendorSeq}/broadcasts")
    public ResponseEntity<List<BroadcastDTO>> getBroadcastsByVendorAndDateRange(
            @PathVariable long vendorSeq,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);

        List<BroadcastDTO> broadcasts = broadcastService.getBroadcastsByVendorAndDateRange(vendorSeq, startDateTime, endDateTime);
        return ResponseEntity.ok(broadcasts);
    }



}
