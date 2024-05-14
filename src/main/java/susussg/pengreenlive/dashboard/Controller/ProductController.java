package susussg.pengreenlive.dashboard.Controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping("/product-list/{vendorSeq}")
  public ResponseEntity<List<ProductDTO>> getProductsByVendor(@PathVariable Long vendorSeq) {
    List<ProductDTO> products = productService.findProductsByVendor(vendorSeq);
    return ResponseEntity.ok(products);
  }

  @PostMapping("/products")
  public ResponseEntity<String> registerProduct(@RequestBody ProductDTO productDTO, @RequestParam Long vendorSeq, @RequestParam Long channelSeq) {
    try {
      if (productService.registerProduct(productDTO, vendorSeq, channelSeq)) {
        log.info("vendorSeq" + vendorSeq);
        log.info("channelSeq" + channelSeq);
        return ResponseEntity.ok("Product successfully registered");
      } else {
        log.info("vendorSeq" + vendorSeq);
        log.info("channelSeq" + channelSeq);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register product");
      }
    } catch (Exception e) {
      log.info("vendorSeq" + vendorSeq);
      log.info("channelSeq" + channelSeq);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during product registration: " + e.getMessage());
    }
  }

  @GetMapping("/categorycodes")
  public ResponseEntity<List<ProductDTO>> getCategoryCodes() {
    List<ProductDTO> productCategoryCodes = productService.getAllCategoryCodes();
    return ResponseEntity.ok(productCategoryCodes);
  }


}
