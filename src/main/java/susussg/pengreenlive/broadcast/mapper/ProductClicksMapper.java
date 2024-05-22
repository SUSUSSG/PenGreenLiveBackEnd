package susussg.pengreenlive.broadcast.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import susussg.pengreenlive.broadcast.dto.BroadcastProductDTO;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductClicksMapper {
//    void insertProductClick(ProductClick productClick);
//    List<BroadcastProductDTO> getProductsByBroadcastSeq(@Param("broadcastSeq") long broadcastSeq);
    void incrementClickCount(@Param("broadcastSeq") Long broadcastSeq, @Param("productSeq") Long productSeq);
    void updateAverageClicks(@Param("broadcastSeq") long broadcastSeq);
    List<Map<String, Object>> getOrderAndClickCounts(@Param("broadcastSeq") long broadcastSeq);

    void updateProductConversionRate(@Param("broadcastSeq") long broadcastSeq,
                                     @Param("productSeq") long productSeq,
                                     @Param("orderCount") int orderCount);

    void updateBroadcastConversionRate(@Param("broadcastSeq") long broadcastSeq);
}
