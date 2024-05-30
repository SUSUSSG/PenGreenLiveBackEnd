package susussg.pengreenlive.order.controller;

import io.swagger.v3.oas.annotations.Operation;
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

  @GetMapping("/orders")
  public ResponseEntity<List<ReviewDTO>> getOrdersByUser() {
    String userUUID = securityService.getCurrentUserUuid();
    List<ReviewDTO> orders = reviewService.findOrdersByUser(userUUID);
    return ResponseEntity.ok(orders);
  }

  @Operation(summary = "리뷰 작성 안된 주문건 조회", description = "주문내역 중 리뷰가 작성되지 않은 주문건을 조회합니다.")
  @GetMapping("/unreviewed-orders")
  public ResponseEntity<List<ReviewDTO>> getUnreviewedOrdersByUser() {
    String userUuid = securityService.getCurrentUserUuid();
    List<ReviewDTO> orders = reviewService.findUnreviewedOrdersByUser(userUuid);
    return ResponseEntity.ok(orders);
  }

  @Operation(summary = "리뷰 작성이 완료된 주문건 조회", description = "주문내역 중 리뷰가 작성된 주문건을 조회합니다.")
  @GetMapping("/reviewed-orders")
  public ResponseEntity<List<ReviewDTO>> getReviewedOrdersByUser() {
    String userUuid = securityService.getCurrentUserUuid();
    List<ReviewDTO> orders = reviewService.findReviewedOrdersByUser(userUuid);
    return ResponseEntity.ok(orders);
  }

  @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.")
  @DeleteMapping("/reviews/{reviewSeq}")
  public ResponseEntity<String> deleteReview(@PathVariable long reviewSeq
          , @RequestParam("productSeq") long productSeq, @RequestParam("orderSeq") long orderSeq) {

    try {
      String userUuid = securityService.getCurrentUserUuid();
      reviewService.deleteReview(userUuid, reviewSeq, productSeq, orderSeq);
      return ResponseEntity.ok("리뷰 삭제가 완료되었습니다.");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("리뷰 삭제에 실패했습니다: " + e.getMessage());
    }
  }

  @Operation(summary = "리뷰 작성", description = "라뷰를 등록합니다.")
  @PostMapping("/reviews")
  public ResponseEntity<String> addReview(@RequestBody ReviewDTO review) {
    try {
      String userUuid = securityService.getCurrentUserUuid();
      review.setUserUUID(userUuid);
      reviewService.addReview(review);
      return ResponseEntity.ok("리뷰 등록이 완료되었습니다.");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("리뷰 등록에 실패했습니다: " + e.getMessage());
    }
  }
}
