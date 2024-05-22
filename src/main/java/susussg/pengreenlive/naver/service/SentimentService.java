package susussg.pengreenlive.naver.service;

public interface SentimentService {

  String ReviewsByProductSeq(Long productSeq);

  String SentimentReviews(String content);

}
