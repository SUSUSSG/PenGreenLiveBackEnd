package susussg.pengreenlive.dashboard.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import susussg.pengreenlive.dashboard.DTO.GreenLabelDTO;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;
import susussg.pengreenlive.dashboard.DTO.VendorProductListDTO;

@Mapper
public interface ProductMapper {

  List<ProductDTO> findAllProducts();

  List<VendorProductListDTO> findProductsByVendor(Long venderSeq);

  void insertProduct(ProductDTO product);

  void insertProductStock(Long productSeq, int productStock);

  void insertChannelSalesProduct(@Param("productSeq") Long productSeq, @Param("vendorSeq") Long vendorSeq, @Param("channelSeq") Long channelSeq);

  List<ProductDTO> selectAllCategoryCodes();

  void updateProduct(ProductDTO productDTO);

  void updateProductStock(@Param("productSeq") Long productSeq, @Param("productStock") int productStock);

  void deleteChannelSalesProduct(Long vendorSeq, Long productSeq);

  void insertProductGreenLabel(GreenLabelDTO greenLabelDTO);

  List<ProductDTO> selectProductWithLabelsByVendor(@Param("vendorSeq") Long vendorSeq);
}
