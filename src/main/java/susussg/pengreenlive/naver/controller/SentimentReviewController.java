package susussg.pengreenlive.naver.controller;

import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.naver.service.ReviewSummaryService;
import susussg.pengreenlive.naver.service.SentimentService;

@RestController
@Log4j2
@RequestMapping("/api")
public class SentimentReviewController {

  @Autowired
  private final SentimentService sentimentService;

  @Autowired
  private final ReviewSummaryService reviewSummaryService;

  public SentimentReviewController(SentimentService sentimentService,
      ReviewSummaryService reviewSummaryService) {
    this.sentimentService = sentimentService;
    this.reviewSummaryService = reviewSummaryService;
  }

  @PostMapping("/review/sentiment")
  public ResponseEntity<String> sentimentReviewsByProductSeq(@RequestParam Long productSeq) {

    String reviews = reviewSummaryService.ReviewsByProductSeq(productSeq);
    String summary = sentimentService.SentimentReviews(reviews);
    log.info("summary: " + summary);

    return ResponseEntity.ok(summary);
  }

  @GetMapping("/review/sentiment/daily")
  public ResponseEntity<Map<String, Map<String, Double>>> getDailySentimentReviews(@RequestParam Long productSeq) {
    Map<String, Map<String, Double>> dailySentiments = reviewSummaryService.getSentimentByDate(productSeq);
    return ResponseEntity.ok(dailySentiments);
  }
}
