package susussg.pengreenlive.dashboard.Service;

import java.util.List;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;

public interface ProductService {

  List<ProductDTO> findAllProducts();

  boolean registerProduct(ProductDTO productDTO, Long vendorSeq, Long channelSeq);

  List<ProductDTO> getAllCategoryCodes();

  List<ProductDTO> findProductsByVendor(Long venderSeq);

  void updateProduct(Long productSeq, ProductDTO productDTO);

}
