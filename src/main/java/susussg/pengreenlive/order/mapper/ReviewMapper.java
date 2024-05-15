package susussg.pengreenlive.order.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import susussg.pengreenlive.order.dto.ReviewDTO;

@Mapper
public interface ReviewMapper {

  List<ReviewDTO> findOrdersByUser(@Param("userUuid") String userUuid);

  List<ReviewDTO> findUnreviewedOrdersByUser(@Param("userUuid") String userUuid);

  List<ReviewDTO> findReviewedOrdersByUser(@Param("userUuid") String userUuid);

}
