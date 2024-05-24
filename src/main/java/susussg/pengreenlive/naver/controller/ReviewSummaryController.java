package susussg.pengreenlive.naver.controller;

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

  @GetMapping("/review/{productSeq}")
  public String getReviewsByProductSeq(@PathVariable Long productSeq) {
    return reviewSummaryService.ReviewsByProductSeq(productSeq);
  }

  @PostMapping("/review/summarize")
  public ResponseEntity<String> summarizeReviewsByProductSeq(@RequestParam Long productSeq) {

    String reviews = reviewSummaryService.ReviewsByProductSeq(productSeq);

    String summary = reviewSummaryService.SummarizeReviews(reviews);
    log.info("summary: " + summary);

    return ResponseEntity.ok(summary);
  }

  @GetMapping("/reviewlist")
  public ResponseEntity<List<String>> getReviewByProduct(@RequestParam Long productSeq) {
    List<String> reviewsList = reviewSummaryService.getReviewByProduct(productSeq);
    return ResponseEntity.ok(reviewsList);
  }


}
