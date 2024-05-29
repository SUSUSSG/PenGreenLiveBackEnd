package susussg.pengreenlive.broadcast.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;
import susussg.pengreenlive.broadcast.service.BroadcastStatisticsService;
import susussg.pengreenlive.login.service.SecurityService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/broadcasts/statistics")
@Log4j2
public class BroadcastStatisticsController {

    @Autowired
    private BroadcastStatisticsService broadcastStatisticsService;

    @Autowired
    private SecurityService securityService;

    /**
     * 방송 통계를 생성합니다.
     * @param broadcastStatistics 방송 통계 정보
     * @return 응답 상태
     */
    @PostMapping
    public ResponseEntity<Void> createBroadcastStatistics(@RequestBody BroadcastStatistics broadcastStatistics) {
        broadcastStatisticsService.insertBroadcastStatistics(broadcastStatistics);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 방송 통계를 조회합니다.
     * @param broadcastSeq 방송 시퀀스 ID
     * @return 방송 통계 정보
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
     * @param broadcastSeq 방송 시퀀스 ID
     * @return 응답 상태
     */
    @PatchMapping("/{broadcastSeq}/likes/increment")
    public ResponseEntity<Void> updateLikesCount(@PathVariable("broadcastSeq") long broadcastSeq) {
        broadcastStatisticsService.updateLikesCount(broadcastSeq);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 방송 통계테이블의 좋아요 개수를 1 감소시킵니다.
     * @param broadcastSeq 방송 시퀀스 ID
     * @return 응답 상태
     */
    @PatchMapping("/{broadcastSeq}/likes/decrement")
    public ResponseEntity<Void> decrementLikesCount(@PathVariable("broadcastSeq") long broadcastSeq) {
        broadcastStatisticsService.decrementLikesCount(broadcastSeq);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 방송 통계 테이블의 평균 시청자 수를 업데이트합니다.
     * @param broadcastSeq 방송 시퀀스 ID
     * @param averageViewerCount 평균 시청자 수
     * @return 응답 상태
     */
    @PatchMapping("/{broadcastSeq}/average-viewer")
    public ResponseEntity<Void> updateAverageViewerCount(@PathVariable("broadcastSeq") long broadcastSeq, @RequestParam int averageViewerCount) {
        broadcastStatisticsService.updateAverageViewerCount(broadcastSeq, averageViewerCount);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 방송 통계테이블의 최대 시청자 수를 업데이트합니다.
     * @param broadcastSeq 방송 시퀀스 ID
     * @param maxViewerCount 최대 시청자 수
     * @return 응답 상태
     */
    @PatchMapping("/{broadcastSeq}/max-viewer")
    public ResponseEntity<Void> updateMaxViewerCount(@PathVariable("broadcastSeq") long broadcastSeq, @RequestParam int maxViewerCount) {
        broadcastStatisticsService.updateMaxViewerCount(broadcastSeq, maxViewerCount);
        return ResponseEntity.ok().build();
    }

    /**
     * 평균 시청자, 최고 시청자, 방송 진행 시간을 업데이트합니다.
     * @param broadcastSeq 방송 시퀀스 ID
     * @param statistics 방송 통계 정보
     * @return 응답 상태
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
     * 누적 시청자 수를 증가시킵니다.
     * @param broadcastSeq 방송 시퀀스 ID
     * @param viewedBroadcastsCookie 시청한 방송 쿠키
     * @param response 응답 객체
     * @return 응답 상태
     */
    @PatchMapping("/{broadcastSeq}/viewsCount")
    public ResponseEntity<Void> incrementViewsCount(@PathVariable("broadcastSeq") long broadcastSeq,
                                                    @CookieValue(value = "viewedBroadcasts", required = false) Cookie viewedBroadcastsCookie,
                                                    HttpServletResponse response) {
        String cookieName = "viewedBroadcasts";
        String cookieValue = "";
        boolean isNewCookie = true;

        if (viewedBroadcastsCookie != null) {
            cookieValue = viewedBroadcastsCookie.getValue();
            isNewCookie = false;
        }

        if (!cookieValue.contains("[" + broadcastSeq + "]")) {
            broadcastStatisticsService.incrementViewsCount(broadcastSeq);

            if (!cookieValue.isEmpty()) {
                cookieValue += "_";
            }
            cookieValue += "[" + broadcastSeq + "]";

            Cookie newViewedBroadcastsCookie = new Cookie(cookieName, cookieValue);
            newViewedBroadcastsCookie.setMaxAge(3600);
            newViewedBroadcastsCookie.setPath("/");

            response.addCookie(newViewedBroadcastsCookie);
        }

        return ResponseEntity.ok().build();
    }

    /**
     * 특정 판매자의 기간별 방송 통계를 조회합니다.
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 방송 통계 리스트
     */
    @GetMapping("/vendor")
    public ResponseEntity<List<BroadcastStatistics>> getStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) {

        Long vendorSeq = securityService.getCurrentVendorSeq();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);

        List<BroadcastStatistics> statistics = broadcastStatisticsService.getStatisticsByVendorAndDateRange(vendorSeq, startDateTime, endDateTime);
        return ResponseEntity.ok(statistics);
    }

    /**
     * 평균 방송 진행 시간을 조회합니다.
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 평균 방송 진행 시간
     */
    @GetMapping("/average-broadcast-duration")
    public ResponseEntity<Integer> getAverageBroadcastDuration(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Long vendorSeq = securityService.getCurrentVendorSeq();
        int avgBroadcastDuration = broadcastStatisticsService.getAverageBroadcastDuration(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgBroadcastDuration);
    }

    /**
     * 평균 시청자 수를 조회합니다.
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 평균 시청자 수
     */
    @GetMapping("/average-viewer-count")
    public ResponseEntity<Integer> getAverageViewerCount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Long vendorSeq = securityService.getCurrentVendorSeq();
        int avgViewerCount = broadcastStatisticsService.getAverageViewerCount(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgViewerCount);
    }

    /**
     * 평균 구매 개수를 조회합니다.
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 평균 구매 개수
     */
    @GetMapping("/average-purchase-quantity")
    public ResponseEntity<Integer> getAveragePurchaseQuantity(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Long vendorSeq = securityService.getCurrentVendorSeq();
        int avgPurchaseQuantity = broadcastStatisticsService.getAveragePurchaseQuantity(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgPurchaseQuantity);
    }

    /**
     * 평균 상품 클릭 수를 조회합니다.
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 평균 상품 클릭 수
     */
    @GetMapping("/average-product-clicks")
    public ResponseEntity<Integer> getAverageProductClicks(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Long vendorSeq = securityService.getCurrentVendorSeq();
        int avgProductClicks = broadcastStatisticsService.getAverageProductClicks(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgProductClicks);
    }

    /**
     * 평균 방송 시청 시간을 조회합니다.
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 평균 방송 시청 시간
     */
    @GetMapping("/average-viewing-time")
    public ResponseEntity<Integer> getAverageViewingTime(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Long vendorSeq = securityService.getCurrentVendorSeq();
        int avgViewingTime = broadcastStatisticsService.getAverageViewingTime(vendorSeq, startDate, endDate);
        log.info("vendorSeq {} avgViewingTime {}",vendorSeq, avgViewingTime);

        return ResponseEntity.ok(avgViewingTime);
    }

    /**
     * 평균 좋아요 수를 조회합니다.
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 평균 좋아요 수
     */
    @GetMapping("/average-likes-count")
    public ResponseEntity<Integer> getAverageLikesCount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Long vendorSeq = securityService.getCurrentVendorSeq();
        int avgLikesCount = broadcastStatisticsService.getAverageLikesCount(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgLikesCount);
    }

    /**
     * 평균 구매 금액을 조회합니다.
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 평균 구매 금액
     */
    @GetMapping("/average-purchase-amount")
    public ResponseEntity<Long> getAveragePurchaseAmount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Long vendorSeq = securityService.getCurrentVendorSeq();
        long avgPurchaseAmount = broadcastStatisticsService.getAveragePurchaseAmount(vendorSeq, startDate, endDate);
        return ResponseEntity.ok(avgPurchaseAmount);
    }

    /**
     * 특정 방송의 좋아요를 토글합니다.
     * @param broadcastSeq 방송 시퀀스 ID
     * @return 응답 상태
     */
    @PatchMapping("/{broadcastSeq}/likes/toggle")
    public ResponseEntity<Void> toggleLike(@PathVariable("broadcastSeq") Long broadcastSeq) {
        String userUUID = securityService.getCurrentUserUuid();
        if (userUUID==null) {
            return ResponseEntity.status(HttpStatus.GONE).build();
        }
        broadcastStatisticsService.toggleLike(userUUID, broadcastSeq);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 방송에 대해 사용자가 좋아요를 눌렀는지 확인합니다.
     * @param broadcastSeq 방송 시퀀스 ID
     * @return 좋아요 여부
     */
    @GetMapping("/{broadcastSeq}/likes/check")
    public ResponseEntity<Boolean> checkLike(@PathVariable("broadcastSeq") Long broadcastSeq) {
        String userUUID = securityService.getCurrentUserUuid();
        if (userUUID==null) {
            return ResponseEntity.status(HttpStatus.GONE).body(false);
        }
        boolean isLiked = broadcastStatisticsService.isLikedByUser(userUUID, broadcastSeq);
        return ResponseEntity.ok(isLiked);
    }
}
