package susussg.pengreenlive.naver.service;

import java.util.List;
import java.util.Map;

public interface ReviewSummaryService {

  String ReviewsByProductSeq(Long productSeq);

  String SummarizeReviews(String review);

  List<String> getReviewByProduct(Long productSeq);
  Map<String, Map<String, Double>> getSentimentByDate(Long productSeq);

}
