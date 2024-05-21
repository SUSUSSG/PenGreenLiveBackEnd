package susussg.pengreenlive.naver.service;

import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import susussg.pengreenlive.naver.mapper.ReviewSummaryMapper;

@Log4j2
@Service
public class ReviewSummaryServiceImpl implements ReviewSummaryService {

  private final ReviewSummaryMapper reviewSummaryMapper;

  @Autowired
  public ReviewSummaryServiceImpl(ReviewSummaryMapper reviewSummaryMapper) {
    this.reviewSummaryMapper = reviewSummaryMapper;
  }


  @Override
  public String summarizeReviewsByProductSeq(Long productSeq) {

    List<String> content = reviewSummaryMapper.getReviewContentsByProductSeq(productSeq);
    String result = String.join("", content);
    log.info("내용!!!!!!!!!!!" + content);
    log.info("결과!!!!!!!" + result);
    return result;

  }
}
