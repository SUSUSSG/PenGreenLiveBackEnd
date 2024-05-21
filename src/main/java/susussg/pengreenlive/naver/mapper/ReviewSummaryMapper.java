package susussg.pengreenlive.naver.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewSummaryMapper {

  List<String> getReviewContentsByProductSeq(Long productSeq);

}
