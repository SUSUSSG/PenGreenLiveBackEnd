package susussg.pengreenlive.order.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.login.service.SecurityService;
import susussg.pengreenlive.order.dto.ReviewDTO;
import susussg.pengreenlive.order.service.ReviewService;

@RestController
@Log4j2
@RequestMapping("/api")
public class ReviewController {

  @Autowired
  private final ReviewService reviewService;

  @Autowired
  private SecurityService securityService;

  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @GetMapping("/orders/{userUuid}")
  public ResponseEntity<List<ReviewDTO>> getOrdersByUser(@PathVariable String userUuid) {
    List<ReviewDTO> orders = reviewService.findOrdersByUser(userUuid);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/unreviewed-orders")
  public ResponseEntity<List<ReviewDTO>> getUnreviewedOrdersByUser() {
    String userUuid = securityService.getCurrentUserUuid();
    List<ReviewDTO> orders = reviewService.findUnreviewedOrdersByUser(userUuid);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/reviewed-orders")
  public ResponseEntity<List<ReviewDTO>> getReviewedOrdersByUser() {
    String userUuid = securityService.getCurrentUserUuid();
    List<ReviewDTO> orders = reviewService.findReviewedOrdersByUser(userUuid);
    return ResponseEntity.ok(orders);
  }

  @DeleteMapping("/reviews/{reviewSeq}")
  public ResponseEntity<String> deleteReview(@PathVariable long reviewSeq) {
    try {
      String userUuid = securityService.getCurrentUserUuid();
      reviewService.deleteReview(userUuid, reviewSeq);
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
