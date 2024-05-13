package susussg.pengreenlive.dashboard.Service;

import java.util.List;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;

public interface ProductService {

  List<ProductDTO> findAllProducts();

  boolean registerProduct(ProductDTO productDTO);

  List<ProductDTO> getAllCategoryCodes();

}
