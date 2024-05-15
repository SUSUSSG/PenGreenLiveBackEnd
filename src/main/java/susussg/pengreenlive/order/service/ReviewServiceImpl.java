package susussg.pengreenlive.order.service;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
