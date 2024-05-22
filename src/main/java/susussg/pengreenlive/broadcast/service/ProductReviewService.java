package susussg.pengreenlive.broadcast.service;

import java.util.List;
import susussg.pengreenlive.broadcast.dto.ProductReviewDTO;

public interface ProductReviewService {

  List<ProductReviewDTO> getReviewsByProductSeq(Long productSeq);

}
