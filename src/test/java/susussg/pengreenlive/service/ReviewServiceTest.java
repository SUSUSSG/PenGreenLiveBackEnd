package susussg.pengreenlive.service;


import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.dashboard.DTO.VendorProductListDTO;
import susussg.pengreenlive.order.dto.ReviewDTO;
import susussg.pengreenlive.order.service.ReviewService;

@Log4j2
@SpringBootTest
public class ReviewServiceTest {

  private final ReviewService reviewService;

  @Autowired
  public ReviewServiceTest(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @Test
  public void getOrderlistTest(){

    String userUUID = "a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6";
    List<ReviewDTO> reviewDTOList = reviewService.findOrdersByUser(userUUID);

    log.info("주문목록" + reviewDTOList);
  }

  @Test
  public void getUnreviewedOrderListTest(){
    String userUUID = "a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6";
    List<ReviewDTO> reviewDTOList = reviewService.findUnreviewedOrdersByUser(userUUID);
    log.info("리뷰 작성 필요 주문 목록 : " + reviewDTOList);
  }

  @Test
  public void getReviewListTest(){
    String userUUID = "f23a72e0-1347-11ef-b085-f220affc9a21";
    List<ReviewDTO> reviewDTOList = reviewService.findReviewedOrdersByUser(userUUID);
    log.info("리뷰 작성 완료 주문 목록 : " + reviewDTOList);
  }


  @Test
  public void addReviewTest(){

    String userUUID = "f23a72e0-1347-11ef-b085-f220affc9a21";

      ReviewDTO reviewDTO = ReviewDTO.builder()
          .productSeq(4L)
          .userUUID(userUUID)
          .reviewContent("상품이 너무 마음에 드네요")
          .reviewTime(LocalDateTime.now())
          .build();

      reviewService.addReview(reviewDTO);
      log.info("리뷰 등록: {}", reviewDTO);
  }

  @Test
  public void testDeleteProduct() {

    String userUUID = "f23a72e0-1347-11ef-b085-f220affc9a21";
    reviewService.deleteReview(userUUID, 20L, 4l, 1l);

  }

}

