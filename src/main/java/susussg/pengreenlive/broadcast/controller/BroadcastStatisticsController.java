package susussg.pengreenlive.broadcast.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;
import susussg.pengreenlive.broadcast.service.BroadcastStatisticsService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/broadcasts/statistics")
@Log4j2
public class BroadcastStatisticsController {
    @Autowired
    private BroadcastStatisticsService broadcastStatisticsService;

    /**
     * 방송통계를 생성합니다.
     * @param broadcastStatistics
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> createBroadcastStatistics(@RequestBody BroadcastStatistics broadcastStatistics) {
        broadcastStatisticsService.insertBroadcastStatistics(broadcastStatistics);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 방송 통계를 조회합니다.
     * @param broadcastSeq
     * @return
     */
    @GetMapping("/{broadcastSeq}")
    public ResponseEntity<BroadcastStatistics> getBroadcastStatistics(@PathVariable("broadcastSeq") long broadcastSeq) {
        BroadcastStatistics broadcastStatistics = broadcastStatisticsService.findById(broadcastSeq);
        if (broadcastStatistics != null) {
            return ResponseEntity.ok(broadcastStatistics);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 특정 방송 통계테이블의 좋아요 개수를 1 증가시킵니다.
     * @param broadcastSeq
     * @return
     */
    @PatchMapping("/{broadcastSeq}/likes/increment")
    public ResponseEntity<Void> updateLikesCount(@PathVariable("broadcastSeq") long broadcastSeq) {
        broadcastStatisticsService.updateLikesCount(broadcastSeq);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 방송 통계테이블의 좋아요 개수를 1 감소시킵니다.
     * @param broadcastSeq
     * @return
     */
    @PatchMapping("/{broadcastSeq}/likes/decrement")
    public ResponseEntity<Void> decrementLikesCount(@PathVariable("broadcastSeq") long broadcastSeq) {
        broadcastStatisticsService.decrementLikesCount(broadcastSeq);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 방송 통계 테이블의 평균 시청자수를 업데이트합니다.
     * @param broadcastSeq
     * @param averageViewerCount
     * @return
     */
    @PatchMapping("/{broadcastSeq}/average-viewer")
    public ResponseEntity<Void> updateAverageViewerCount(@PathVariable("broadcastSeq") long broadcastSeq, @RequestParam int averageViewerCount) {
        broadcastStatisticsService.updateAverageViewerCount(broadcastSeq, averageViewerCount);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 방송 통계테이블의 최대 시청자수를 업데이트합니다.
     * @param broadcastSeq
     * @param maxViewerCount
     * @return
     */
    @PatchMapping("/{broadcastSeq}/max-viewer")
    public ResponseEntity<Void> updateMaxViewerCount(@PathVariable("broadcastSeq") long broadcastSeq, @RequestParam int maxViewerCount) {
        broadcastStatisticsService.updateMaxViewerCount(broadcastSeq, maxViewerCount);
        return ResponseEntity.ok().build();
    }

    /**
     * 평균 시청자, 최고 시청자, 방송 진행 시간을 업데이트합니다.
     * @param broadcastSeq
     * @param statistics
     * @return
     */
    @PatchMapping("/{broadcastSeq}")
    public ResponseEntity<Void> updateBroadcastStatistics(
            @PathVariable("broadcastSeq") long broadcastSeq,
            @RequestBody BroadcastStatistics statistics) {
        broadcastStatisticsService.updateBroadcastStatistics(broadcastSeq, statistics);
        log.info(statistics.toString());
        return ResponseEntity.ok().build();
    }

    /**
     * 누적 시청자 수 증가
     * @param broadcastSeq
     * @param
     * @param
     * @param
     * @return
     */
    @PatchMapping("/{broadcastSeq}/viewsCount")
    public ResponseEntity incrementViewsCount(@PathVariable("broadcastSeq") long broadcastSeq) {
//        @CookieValue(value = "viewedBroadcasts", required = false) Cookie viewedBroadcastsCookie,
//        HttpServletResponse response,
//        HttpServletRequest request
//        // 초기 쿠키 값 설정
//        String cookieName = "viewedBroadcasts";
//        String cookieValue = "";
//        boolean isNewCookie = true;
//
//        // 기존 쿠키가 있는지 확인
//        if (viewedBroadcastsCookie != null) {
//            cookieValue = viewedBroadcastsCookie.getValue();
//            isNewCookie = false;
//        }
//
//        // 방송 시퀀스가 쿠키 값에 없는 경우
//        if (!cookieValue.contains("[" + broadcastSeq + "]")) {
//            // 조회수 증가
//            broadcastStatisticsService.incrementViewsCount(broadcastSeq);
//
//            // 쿠키 값 업데이트
//            if (!cookieValue.isEmpty()) {
//                cookieValue += "_";
//            }
//            cookieValue += "[" + broadcastSeq + "]";
//
//            // 새로운 쿠키 생성 또는 기존 쿠키 업데이트
//            Cookie newViewedBroadcastsCookie = new Cookie(cookieName, cookieValue);
//            newViewedBroadcastsCookie.setMaxAge(3600); // 1시간 유효
//            newViewedBroadcastsCookie.setPath("/"); // 전체 경로에 대해 쿠키 유효
//
//            // 응답에 새로운 쿠키 추가
//            response.addCookie(newViewedBroadcastsCookie);
//        }
        broadcastStatisticsService.incrementViewsCount(broadcastSeq);

        // 조회수 증가가 필요 없는 경우에도 OK 응답 반환
        return ResponseEntity.ok().build();
    }


    /**
     * 특정 판매자의 기간별 방송 통계를 조회합니다.
     * @param vendorSeq
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/vendor/{vendorSeq}")
    public ResponseEntity<List<BroadcastStatistics>> getStatistics(
            @PathVariable long vendorSeq,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);

        List<BroadcastStatistics> statistics = broadcastStatisticsService.getStatisticsByVendorAndDateRange(vendorSeq, startDateTime, endDateTime);
        return ResponseEntity.ok(statistics);
    }

    // 평균 방송 진행 시간
    @GetMapping("/average-broadcast-duration")
    public ResponseEntity<Integer> getAverageBroadcastDuration(
            @RequestParam long vendorSeq,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        int avgBroadcastDuration = broadcastStatisticsService.getAverageBroadcastDuration(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgBroadcastDuration);
    }

    // 평균 시청자 수
    @GetMapping("/average-viewer-count")
    public ResponseEntity<Integer> getAverageViewerCount(
            @RequestParam long vendorSeq,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        int avgViewerCount = broadcastStatisticsService.getAverageViewerCount(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgViewerCount);
    }

    // 평균 구매 개수
    @GetMapping("/average-purchase-quantity")
    public ResponseEntity<Integer> getAveragePurchaseQuantity(
            @RequestParam long vendorSeq,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        int avgPurchaseQuantity = broadcastStatisticsService.getAveragePurchaseQuantity(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgPurchaseQuantity);
    }

    // 평균 상품 클릭수
    @GetMapping("/average-product-clicks")
    public ResponseEntity<Integer> getAverageProductClicks(
            @RequestParam long vendorSeq,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        int avgProductClicks = broadcastStatisticsService.getAverageProductClicks(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgProductClicks);
    }

    // 평균 방송 시청 시간
    @GetMapping("/average-viewing-time")
    public ResponseEntity<Integer> getAverageViewingTime(
            @RequestParam long vendorSeq,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        int avgViewingTime = broadcastStatisticsService.getAverageViewingTime(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgViewingTime);
    }

    // 평균 좋아요 수
    @GetMapping("/average-likes-count")
    public ResponseEntity<Integer> getAverageLikesCount(
            @RequestParam long vendorSeq,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        int avgLikesCount = broadcastStatisticsService.getAverageLikesCount(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgLikesCount);
    }

    // 평균 구매 금액
    @GetMapping("/average-purchase-amount")
    public ResponseEntity<Long> getAveragePurchaseAmount(
            @RequestParam long vendorSeq,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        long avgPurchaseAmount = broadcastStatisticsService.getAveragePurchaseAmount(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgPurchaseAmount);
    }




}
