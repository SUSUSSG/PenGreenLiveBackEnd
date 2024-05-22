package susussg.pengreenlive.broadcast.service;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.broadcast.dto.ProductReviewDTO;
import susussg.pengreenlive.broadcast.mapper.ProductReviewMapper;

@Log4j2
@Service
public class ProductReviewServiceImpl implements ProductReviewService{

  @Autowired
  private final ProductReviewMapper productReviewMapper;

  public ProductReviewServiceImpl(ProductReviewMapper productReviewMapper) {
    this.productReviewMapper = productReviewMapper;
  }

  @Override
  public List<ProductReviewDTO> getReviewsByProductSeq(Long productSeq) {
    return productReviewMapper.getReviewsByProductSeq(productSeq);
  }
}
