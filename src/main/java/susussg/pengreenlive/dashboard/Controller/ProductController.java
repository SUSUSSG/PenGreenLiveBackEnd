package susussg.pengreenlive.dashboard.Controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;
import susussg.pengreenlive.dashboard.DTO.VendorProductListDTO;
import susussg.pengreenlive.dashboard.Service.ProductService;

@RestController
@Log4j2
@RequestMapping("/api")
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
  public ResponseEntity<List<VendorProductListDTO>> getProductsByVendor(@PathVariable Long vendorSeq) {
    List<VendorProductListDTO> products = productService.findProductsByVendor(vendorSeq);
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

  @PutMapping("/product/{productSeq}")
  public ResponseEntity<String> updateProduct(@PathVariable Long productSeq, @RequestBody ProductDTO productDTO) {
    try {
      System.out.println("Updating product with productSeq: " + productSeq);
      productService.updateProduct(productSeq, productDTO);
      return ResponseEntity.ok("Product successfully updated");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed to update product: " + e.getMessage());
    }
  }

  @DeleteMapping("/product/{vendorSeq}/{productSeq}")
  public ResponseEntity<String> deleteProduct(@PathVariable Long vendorSeq, @PathVariable Long productSeq) {
    try {
      productService.deleteProduct(vendorSeq, productSeq);
      return ResponseEntity.ok("상품삭제에 성공했습니다.");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("상품 삭제에 실패했습니다 " + e.getMessage());
    }
  }


}
