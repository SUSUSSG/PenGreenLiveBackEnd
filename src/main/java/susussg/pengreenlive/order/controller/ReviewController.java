package susussg.pengreenlive.order.controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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

  @GetMapping("/unreviewed-orders/{userUuid}")
  public ResponseEntity<List<ReviewDTO>> getOrdersByUser(@PathVariable String userUuid) {
    List<ReviewDTO> orders = reviewService.findOrdersByUser(userUuid);
    return ResponseEntity.ok(orders);
  }


}
