package susussg.pengreenlive.order.service;


import java.util.List;
import susussg.pengreenlive.order.dto.ReviewDTO;

public interface ReviewService {

  List<ReviewDTO> findOrdersByUser(String userUuid);

  List<ReviewDTO> findUnreviewedOrdersByUser(String userUuid);

  List<ReviewDTO> findReviewedOrdersByUser(String userUuid);

  void deleteReview(String userUuid, long reviewSeq, long productSeq, long orderSeq);

  void addReview(ReviewDTO review);
}
