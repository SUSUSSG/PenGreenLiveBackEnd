package susussg.pengreenlive.order.controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.order.dto.ReviewDTO;
import susussg.pengreenlive.order.service.ReviewService;

@RestController
@Log4j2
public class ReviewController {

  @Autowired
  private final ReviewService reviewService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @GetMapping("/orders/{userUuid}")
  public ResponseEntity<List<ReviewDTO>> getOrdersByUser(@PathVariable String userUuid) {
    List<ReviewDTO> orders = reviewService.findOrdersByUser(userUuid);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/unreviewed-orders/{userUuid}")
  public ResponseEntity<List<ReviewDTO>> getUnreviewedOrdersByUser(@PathVariable String userUuid) {
    List<ReviewDTO> orders = reviewService.findUnreviewedOrdersByUser(userUuid);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/reviewed-orders/{userUuid}")
  public ResponseEntity<List<ReviewDTO>> getReviewedOrdersByUser(@PathVariable String userUuid) {
    List<ReviewDTO> orders = reviewService.findReviewedOrdersByUser(userUuid);
    return ResponseEntity.ok(orders);
  }

  @DeleteMapping("/reviews/{userUuid}/{reviewSeq}")
  public ResponseEntity<String> deleteReview(@PathVariable String userUuid, @PathVariable long reviewSeq
                                                , @RequestParam("productSeq") long productSeq, @RequestParam("orderSeq") long orderSeq) {

    try {
      reviewService.deleteReview(userUuid, reviewSeq, productSeq, orderSeq);
      return ResponseEntity.ok("리뷰 삭제가 완료되었습니다.");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("리뷰 삭제에 실패했습니다: " + e.getMessage());
    }
  }

  @PostMapping("/reviews")
  public ResponseEntity<String> addReview(@RequestBody ReviewDTO review) {
    try {
      reviewService.addReview(review);
      return ResponseEntity.ok("리뷰 등록이 완료되었습니다.");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("리뷰 등록에 실패했습니다: " + e.getMessage());
    }
  }
}
