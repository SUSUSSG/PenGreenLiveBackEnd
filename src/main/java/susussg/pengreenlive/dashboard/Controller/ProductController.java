package susussg.pengreenlive.dashboard.Controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;
import susussg.pengreenlive.dashboard.Service.ProductService;

@RestController
@Log4j2
public class ProductController {

  @Autowired
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/product-list")
  public ResponseEntity<List<ProductDTO>> getAllProducts() {
    List<ProductDTO> products = productService.findAllProducts();
    return ResponseEntity.ok(products);
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerProduct(@RequestBody ProductDTO productDTO) {
    try {
      if (productService.registerProduct(productDTO)) {
        return ResponseEntity.ok("Product successfully registered");
      } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register product");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during product registration: " + e.getMessage());
    }
  }

  @GetMapping("/codes")
  public ResponseEntity<List<ProductDTO>> getCategoryCodes() {
    List<ProductDTO> productCategoryCodes = productService.getAllCategoryCodes();
    return ResponseEntity.ok(productCategoryCodes);
  }

}
