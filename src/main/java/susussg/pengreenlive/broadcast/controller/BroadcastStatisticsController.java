package susussg.pengreenlive.broadcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.broadcast.dto.BroadcastStatistics;
import susussg.pengreenlive.broadcast.service.BroadcastStatisticsService;

@RestController
@RequestMapping("/broadcasts/statistics")
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
     * 방송 통계를 조회합니다.
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
}
