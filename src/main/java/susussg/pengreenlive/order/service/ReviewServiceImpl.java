package susussg.pengreenlive.order.service;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.order.dto.ReviewDTO;
import susussg.pengreenlive.order.mapper.ReviewMapper;

@Log4j2
@Service
public class ReviewServiceImpl implements ReviewService {

 private final ReviewMapper reviewMapper;

 @Autowired
 public ReviewServiceImpl(ReviewMapper reviewMapper) {
    this.reviewMapper = reviewMapper;
  }

  @Override
  public List<ReviewDTO> findOrdersByUser(String userUuid) {
    return reviewMapper.findOrdersByUser(userUuid);
  }

  @Override
  public List<ReviewDTO> findUnreviewedOrdersByUser(String userUuid) {
    return reviewMapper.findUnreviewedOrdersByUser(userUuid);
  }

  @Override
  public List<ReviewDTO> findReviewedOrdersByUser(String userUuid) {
    return reviewMapper.findReviewedOrdersByUser(userUuid);
  }

    @Override
    @Transactional
    public void deleteReview(String userUuid, long reviewSeq, long productSeq, long orderSeq) {
        ReviewDTO review = ReviewDTO.builder()
                .userUUID(userUuid)
                .productSeq(productSeq)
                .orderSeq(orderSeq)
                .reviewYn(false)
                .build();
        reviewMapper.deleteReviewByUserAndReviewSeq(userUuid, reviewSeq);
        reviewMapper.updateReviewYn(review);
    }

    @Override
    @Transactional
    public void addReview(ReviewDTO review) {
        reviewMapper.insertReview(review);
        review.setReviewYn(true);
        reviewMapper.updateReviewYn(review);
    }
}
