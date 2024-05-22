package susussg.pengreenlive.broadcast.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.broadcast.dto.ProductReviewDTO;
import susussg.pengreenlive.broadcast.service.ProductReviewService;
import susussg.pengreenlive.order.dto.ReviewDTO;

@RestController
public class ProductReviewController {

  @Autowired
  private final ProductReviewService productReviewService;

  public ProductReviewController(ProductReviewService productReviewService) {
    this.productReviewService = productReviewService;
  }

  @GetMapping("/broadcast/review")
  public List<ProductReviewDTO> getReviewsByProductSeq(@RequestParam Long productSeq) {
    return productReviewService.getReviewsByProductSeq(productSeq);
  }
}
