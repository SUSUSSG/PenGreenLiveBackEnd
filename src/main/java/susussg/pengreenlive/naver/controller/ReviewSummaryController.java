package susussg.pengreenlive.naver.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.naver.dto.SummaryRequestDTO;
import susussg.pengreenlive.naver.service.ReviewSummaryService;

@RestController
@Log4j2
@RequestMapping("/api")
public class ReviewSummaryController {

  @Autowired
  private final ReviewSummaryService reviewSummaryService;

  public ReviewSummaryController(ReviewSummaryService reviewSummaryService) {
    this.reviewSummaryService = reviewSummaryService;
  }

  @Operation(summary = "상품별 리뷰 조회", description = "상품별 리뷰를 조회합니다.")
  @GetMapping("/review/{productSeq}")
  public String getReviewsByProductSeq(@PathVariable Long productSeq) {
    return reviewSummaryService.ReviewsByProductSeq(productSeq);
  }

  @Operation(summary = "리뷰 내용 요약", description = "상품별 리뷰 내용을 요약합니다.")
  @PostMapping("/review/summarize")
  public ResponseEntity<String> summarizeReviewsByProductSeq(@RequestParam Long productSeq) {

    String reviews = reviewSummaryService.ReviewsByProductSeq(productSeq);

    String summary = reviewSummaryService.SummarizeReviews(reviews);
    log.info("summary: " + summary);

    return ResponseEntity.ok(summary);
  }

  @Operation(summary = "상품별 리뷰 조회", description = "상품별 리뷰를 리스트로 반환받아 조회합니다.")
  @GetMapping("/reviewlist")
  public ResponseEntity<List<String>> getReviewByProduct(@RequestParam Long productSeq) {
    List<String> reviewsList = reviewSummaryService.getReviewByProduct(productSeq);
    return ResponseEntity.ok(reviewsList);
  }


}
