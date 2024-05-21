package susussg.pengreenlive.service;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.naver.service.ReviewSummaryService;
import susussg.pengreenlive.order.dto.ReviewDTO;

@Log4j2
@SpringBootTest
public class ReviewSummaryServiceTest {

  private final ReviewSummaryService reviewSummaryService;

  @Autowired
  public ReviewSummaryServiceTest(ReviewSummaryService reviewSummaryService) {
    this.reviewSummaryService = reviewSummaryService;
  }

  @Test
  public void getProductReviewList(){

    String reviewList = reviewSummaryService.summarizeReviewsByProductSeq(4L);
    log.info("리뷰!!!!!!!!!!!" + reviewList);

  }

}
