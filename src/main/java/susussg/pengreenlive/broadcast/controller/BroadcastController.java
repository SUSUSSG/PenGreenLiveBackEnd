package susussg.pengreenlive.broadcast.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import susussg.pengreenlive.broadcast.dto.*;
import susussg.pengreenlive.broadcast.service.BroadcastService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import susussg.pengreenlive.login.service.SecurityService;
import susussg.pengreenlive.util.Service.ForbiddenWordService;


@RestController
@Log4j2
@RequestMapping("/api")
public class BroadcastController {

    @Autowired
    private final BroadcastService broadcastService;

    @Autowired
    private SecurityService securityService;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();


    @Autowired
    ForbiddenWordService forbiddenWordService;


    public BroadcastController(BroadcastService broadcastService) {
        this.broadcastService = broadcastService;
    }

    @Operation(summary = "카테고리 정보를 조회합니다.", description = "모든 카테고리 정보를 조회합니다.")
    @GetMapping("/broadcast-category")
    public ResponseEntity<List<BroadcastCategoryDTO>> fetchBroadcastCategory() {
        List<BroadcastCategoryDTO> categoryList = broadcastService.getAllCategory();
        return ResponseEntity.ok().body(categoryList);
    }

    // 방송 등록
    @Operation(summary = "방송 정보를 등록합니다.", description = "클라이언트로 부터 방송 등록 정보를 수신하고, 현재 채널Seq와 VendorSeq를 수신하여 방송 정보를 DB에 등록합니다.")
    @PostMapping(value = "/register-broadcast")
    public ResponseEntity<String> registerBroadcast(@RequestBody BroadcastRegistrationRequestDTO broadcastRegisterInfo) {
        Long channelSeq = securityService.getCurrentChannelSeq();
        Long vendorSeq = securityService.getCurrentVendorSeq();
        broadcastRegisterInfo.setChannelSeq(channelSeq);
        broadcastService.registerBroadcast(broadcastRegisterInfo, vendorSeq);
        return ResponseEntity.ok().body("방송 정보가 성공적으로 등록되었습니다.");
    }

    //채널별 상품 목록
    @Operation(summary = "채널별 상품 목록을 표시합니다.", description = "현재 접속자의 VendorSeq를 받아 채널별 상품을 조회합니다.")
    @GetMapping("/channel-sales-product")
    public ResponseEntity<List<ChannelSalesProductDTO>> fetchChannelSalesProduct() {
        Long vendorSeq = securityService.getCurrentVendorSeq();
        List<ChannelSalesProductDTO> channelSalesProducts = broadcastService.getChannelSalesProductAll(vendorSeq);
        return ResponseEntity.ok().body(channelSalesProducts);
    }

    // 방송 예정 목록
    @Operation(summary = "방송 예정 일정을 불러옵니다.", description = "현재 접속한 VendorSeq의 방송 예정 일정을 불러옵니다.")
    @GetMapping("/prepare-broadcasts")
    public List<PrepareBroadcastInfoDTO> fetchUpcomingBroadcasts() {
        log.info("방송 준비 컨트롤러 호출");
        Long vendorSeq = securityService.getCurrentVendorSeq();
        return broadcastService.getUpcomingBroadcastInfo(vendorSeq);
    }

    @Operation(summary = "기간별 방송 목록을 조회합니다.", description = "특정 기간 내의 방송 목록을 조회합니다.")
    @GetMapping("/vendor/broadcasts")
    public ResponseEntity<List<BroadcastDTO>> getBroadcastsByVendorAndDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) {

        Long vendorSeq = securityService.getCurrentVendorSeq();
        log.info("/vendor/broadcasts vendorSeq {}", vendorSeq);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);

        List<BroadcastDTO> broadcasts = broadcastService.getBroadcastsByVendorAndDateRange(vendorSeq, startDateTime, endDateTime);
        return ResponseEntity.ok(broadcasts);
    }

    // 방송시간 update
    @Operation(summary = "방송 시간을 업데이트합니다.", description = "방송의 시작 시간과 종료 시간을 업데이트합니다.")
    @PatchMapping("update/broadcast-time")
    public ResponseEntity<String> updateBroadcastTime(@RequestBody BroadcastTimeDTO broadcastTime) {
        log.info("시간 컨트롤러 호출");
        if (broadcastTime.getAction().equals("start")) {
            broadcastService.startBroadcast(broadcastTime);
            return ResponseEntity.ok("시작 시간 반영 성공");
        } else if (broadcastTime.getAction().equals("end")) {
            broadcastService.endBroadcast(broadcastTime);
            return ResponseEntity.ok("종료 시간 반영 성공");
        } else {
            return ResponseEntity.ok("시간 반영 실패");
        }
    }

    @Operation(summary = "방송 종료 이벤트를 전송합니다.", description = "방송 종료 이벤트를 모든 구독자에게 전송합니다.")
    @GetMapping("/broadcast-end/{broadcastId}")
    public ResponseEntity<String> endBroadcast(@PathVariable("broadcastId") long broadcastId) {
        log.info("broadcast-end 호출");
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("broadcast-end").data(broadcastId));
                emitter.complete();
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
        return ResponseEntity.ok("Broadcast end event sent to all clients");
    }

    @Operation(summary = "SSE 구독을 설정합니다.", description = "SSE를 통해 실시간 이벤트를 구독합니다.")
    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        log.info("SSE 구독 요청 정보가 들어옵니다"); // 로그 추가
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // 타임아웃을 무한대로 설정
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        return emitter;
    }

    @Operation(summary = "금칙어를 추가합니다.", description = "방송 시퀀스와 금칙어를 받아서 금칙어를 추가합니다.")
    @PostMapping("forbidden-words")
    public ResponseEntity<String> addForbiddenWord(@RequestParam("broadcastSeq") String broadcastSeq, @RequestParam("forbiddenWord") String forbiddenWord) {
        try {
            long broadcastSeqLong = Long.parseLong(broadcastSeq);
            log.info("금칙어 추가 broadcast id :" + broadcastSeqLong + " 금지어 : " + forbiddenWord);
            forbiddenWordService.addForbiddenWord(broadcastSeqLong, forbiddenWord);
            return ResponseEntity.ok("금칙어가 성공적으로 등록되었습니다.");
        } catch (NumberFormatException e) {
            log.error("Invalid broadcastSeq format: " + broadcastSeq, e);
            return ResponseEntity.badRequest().body("방송 시퀀스 형식이 잘못되었습니다.");
        } catch (Exception e) {
            log.error("금칙어 등록 중 에러 발생:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("금칙어 등록 중 에러가 발생했습니다.");
        }
    }
}
