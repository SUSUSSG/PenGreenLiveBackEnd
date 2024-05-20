package susussg.pengreenlive.broadcast.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import susussg.pengreenlive.broadcast.dto.BroadcastProductDTO;
import susussg.pengreenlive.broadcast.dto.ProductClick;

import java.util.List;

@Mapper
public interface ProductClicksMapper {
    void insertProductClick(ProductClick productClick);
    List<BroadcastProductDTO> getProductsByBroadcastSeq(@Param("broadcastSeq") long broadcastSeq);
    void incrementClickCount(@Param("broadcastSeq") Long broadcastSeq, @Param("productSeq") Long productSeq);
    void updateAverageClicks(@Param("broadcastSeq") long broadcastSeq);
}
