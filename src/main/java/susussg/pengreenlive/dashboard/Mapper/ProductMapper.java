package susussg.pengreenlive.dashboard.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;

@Mapper
public interface ProductMapper {

  List<ProductDTO> findAllProducts();

  List<ProductDTO> findProductsByVendor(Long venderSeq);

  void insertProduct(ProductDTO product);

  void insertProductStock(Long productSeq, int productStock);

  void insertChannelSalesProduct(@Param("productSeq") Long productSeq, @Param("vendorSeq") Long vendorSeq, @Param("channelSeq") Long channelSeq);

  List<ProductDTO> selectAllCategoryCodes();
}
