package susussg.pengreenlive.dashboard.Service;

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

}
