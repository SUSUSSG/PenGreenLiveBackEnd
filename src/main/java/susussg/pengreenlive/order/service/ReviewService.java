package susussg.pengreenlive.order.service;


import java.util.List;
import susussg.pengreenlive.order.dto.ReviewDTO;

public interface ReviewService {

  List<ReviewDTO> findOrdersByUser(String userUuid);

}
