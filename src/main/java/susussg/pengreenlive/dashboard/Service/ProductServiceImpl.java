package susussg.pengreenlive.dashboard.Service;

import jakarta.transaction.Transactional;
import java.util.Base64;
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
      if (productDTO.getBase64Image() != null) {
        byte[] imageBytes = Base64.getDecoder().decode(productDTO.getBase64Image());
        productDTO.setProductImage(imageBytes);
      }
      productMapper.insertProduct(productDTO);
      Long productSeq = productDTO.getProductSeq();
      productMapper.insertProductStock(productSeq , productDTO.getProductStock());
      productMapper.insertChannelSalesProduct(productSeq , vendorSeq, channelSeq);
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

  @Override
  public void updateProduct(Long productSeq, ProductDTO productDTO) {
    try {
      if (productDTO.getBase64Image() != null) {
        byte[] imageBytes = Base64.getDecoder().decode(productDTO.getBase64Image());
        productDTO.setProductImage(imageBytes);
      }
      productDTO.setProductSeq(productSeq);
      productMapper.updateProduct(productDTO);
      productMapper.updateProductStock(productSeq, productDTO.getProductStock());
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Product update failed", e);
    }
  }


}
