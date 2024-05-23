package susussg.pengreenlive.broadcast.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.broadcast.dto.ProductReviewDTO;

@Mapper
public interface ProductReviewMapper {

  List<ProductReviewDTO> getReviewsByProductSeq(Long productSeq);

}
