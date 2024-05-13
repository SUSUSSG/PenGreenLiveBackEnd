package susussg.pengreenlive.dashboard.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;

@Mapper
public interface ProductMapper {

  List<ProductDTO> findAllProducts();

  void insertProduct(ProductDTO product);

  void insertProductStock(Long productSeq, int productStock);

  List<ProductDTO> selectAllCategoryCodes();
}
