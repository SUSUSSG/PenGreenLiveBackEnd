package susussg.pengreenlive.dashboard.Service;

import java.util.List;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;
import susussg.pengreenlive.dashboard.DTO.VendorProductListDTO;

public interface ProductService {

  List<ProductDTO> findAllProducts();

  boolean registerProduct(ProductDTO productDTO, Long vendorSeq, Long channelSeq);

  List<ProductDTO> getAllCategoryCodes();

  List<VendorProductListDTO> findProductsByVendor(Long venderSeq);

  void updateProduct(Long productSeq, ProductDTO productDTO);

  void deleteProduct(Long vendorSeq, Long productSeq);

  List<ProductDTO> getAllProducts();

}
