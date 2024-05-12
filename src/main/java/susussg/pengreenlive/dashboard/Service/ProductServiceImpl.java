package susussg.pengreenlive.dashboard.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;
import susussg.pengreenlive.dashboard.Mapper.ProductMapper;

@Service
public class ProductServiceImpl implements ProductService{

  private final ProductMapper productMapper;

  @Autowired
  public ProductServiceImpl(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  @Override
  public List<ProductDTO> findAllProducts() {
    return productMapper.findAllProducts();
  }

  @Override
  @Transactional
  public boolean registerProduct(ProductDTO productDTO) {
    try {

      productMapper.insertProduct(productDTO);
      productMapper.insertProductStock(productDTO.getProductSeq(), productDTO.getProductStock());
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("상품 등록 실패", e);
    }
  }

  @Override
  public List<ProductDTO> getAllProductCodes() {
    return productMapper.selectAllProductCodes();
  }


}
