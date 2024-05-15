package susussg.pengreenlive.service;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

}
