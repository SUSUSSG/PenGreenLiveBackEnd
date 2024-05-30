package susussg.pengreenlive.dashboard.Controller;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;
import susussg.pengreenlive.dashboard.DTO.ProductUpdateDTO;
import susussg.pengreenlive.dashboard.DTO.VendorProductListDTO;
import susussg.pengreenlive.dashboard.Service.ProductService;
import susussg.pengreenlive.login.service.SecurityService;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

  private final ProductService productService;
  private final SecurityService securityService;

  @Operation(summary = "상품 목록 조회", description = "전체 상품을 조회합니다.")
  @GetMapping("/product-list")
  public ResponseEntity<List<ProductDTO>> getAllProducts() {
    List<ProductDTO> products = productService.findAllProducts();
    return ResponseEntity.ok(products);
  }

  @Operation(summary = "판매자에 상품 목록 조회", description = "판매자에 따라 상품 목록을 조회합니다.")
  @GetMapping("/product-list/vendor")
  public ResponseEntity<List<VendorProductListDTO>> getProductsByVendor() {
    Long vendorSeq = securityService.getCurrentVendorSeq();
    List<VendorProductListDTO> products = productService.findProductsByVendor(vendorSeq);
    return ResponseEntity.ok(products);
  }

  @Operation(summary = "상품 등록", description = "녹색제품 ID를 인증하고 상품을 등록합니다.")
  @PostMapping("/products")
  public ResponseEntity<String> registerProduct(@RequestBody ProductDTO productDTO) {
    Long vendorSeq = securityService.getCurrentVendorSeq();
    Long channelSeq = securityService.getCurrentChannelSeq();

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

  @Operation(summary = "상품 카테고리 불러옴", description = "상품 카테고리를 가져옵니다.")
  @GetMapping("/categorycodes")
  public ResponseEntity<List<ProductDTO>> getCategoryCodes() {
    List<ProductDTO> productCategoryCodes = productService.getAllCategoryCodes();
    return ResponseEntity.ok(productCategoryCodes);
  }

  @Operation(summary = "상품 정보 수정", description = "상품 정보를 수정합니다.")
  @PutMapping("/product/{productSeq}")
  public ResponseEntity<String> updateProduct(@PathVariable Long productSeq, @RequestBody ProductUpdateDTO productUpdateDTO) {
    try {
      System.out.println("Updating product with productSeq: " + productSeq);
      productService.updateProduct(productSeq, productUpdateDTO);
      return ResponseEntity.ok("상품 정보가 수정되었습니다.");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("Failed to update product: " + e.getMessage());
    }
  }

  @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
  @DeleteMapping("/product/{productSeq}")
  public ResponseEntity<String> deleteProduct(@PathVariable Long productSeq) {
    Long vendorSeq = securityService.getCurrentVendorSeq();
    try {
      productService.deleteProduct(vendorSeq, productSeq);
      return ResponseEntity.ok("상품삭제에 성공했습니다.");
    } catch (Exception e) {
      return ResponseEntity.status(500).body("상품 삭제에 실패했습니다 " + e.getMessage());
    }
  }

  @Operation(summary = "녹색인증 이미지 포함 상품 조회", description = "판매자에 따라 인증받은 녹색ID에 따른 인증 이미지와 목록을 조회합니다.")
  @GetMapping("/product-list-label")
  public List<ProductDTO> getAllProductsWithLabels() {
    Long vendorSeq = securityService.getCurrentVendorSeq();
    return productService.getAllProductsWithLabels(vendorSeq);
  }


}
