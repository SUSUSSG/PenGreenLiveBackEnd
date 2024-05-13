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
  public boolean registerProduct(ProductDTO productDTO, Long vendorSeq, Long channelSeq) {
    try {
      productMapper.insertProduct(productDTO);
      productMapper.insertProductStock(productDTO.getProductSeq(), productDTO.getProductStock());
      productMapper.insertChannelSalesProduct(productDTO.getProductSeq(), vendorSeq, channelSeq);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Product registration failed", e);
    }
  }

  @Override
  public List<ProductDTO> getAllCategoryCodes() {
    return productMapper.selectAllCategoryCodes();
  }

  @Override
  public List<ProductDTO> findProductsByVendor(Long venderSeq) {
    return productMapper.findProductsByVendor(venderSeq);
  }


}
