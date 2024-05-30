package susussg.pengreenlive.broadcast.controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.broadcast.dto.ProductReviewDTO;
import susussg.pengreenlive.broadcast.service.ProductReviewService;
import susussg.pengreenlive.order.dto.ReviewDTO;

@RestController
@RequestMapping("/api")
public class ProductReviewController {

  @Autowired
  private final ProductReviewService productReviewService;

  public ProductReviewController(ProductReviewService productReviewService) {
    this.productReviewService = productReviewService;
  }

  @Operation(summary = "구맺 페이지 내 리뷰 확인", description = "구매페이지 내 상품별 리뷰를 조회합니다.")
  @GetMapping("/broadcast/review")
  public List<ProductReviewDTO> getReviewsByProductSeq(@RequestParam Long productSeq) {
    return productReviewService.getReviewsByProductSeq(productSeq);
  }
}
