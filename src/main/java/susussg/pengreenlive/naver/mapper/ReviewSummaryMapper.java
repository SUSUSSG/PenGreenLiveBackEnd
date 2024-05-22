package susussg.pengreenlive.naver.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewSummaryMapper {

  List<String> getReviewContentsByProductSeq(Long productSeq);
  List<Map<String, Object>> getReviewContentsByProductSeqAndDate(Long productSeq);

}
