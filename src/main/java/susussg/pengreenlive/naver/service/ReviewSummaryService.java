package susussg.pengreenlive.naver.service;

import java.util.List;

public interface ReviewSummaryService {

  String ReviewsByProductSeq(Long productSeq);

  String SummarizeReviews(String review);

  List<String> getReviewByProduct(Long productSeq);

}
