package susussg.pengreenlive.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;
import susussg.pengreenlive.dashboard.DTO.ShopInfoDTO;
import susussg.pengreenlive.dashboard.Service.ProductService;
import susussg.pengreenlive.dashboard.Service.ShopInfoService;

@SpringBootTest
@Log4j2
public class ProductServiceTest {

  @Autowired
  private ProductService productService;

  @Autowired
  public ProductServiceTest(ProductService productService) {
    this.productService = productService;
  }

  @Test
  public void getProductListTest() {

    List<ProductDTO> productDTOList = productService.findAllProducts();
    log.info(productDTOList);
  }

  @Test
  public void addProductTest() throws IOException {

    try {
      File file = new File("C:/Users/kuy06/OneDrive/Desktop/c2.jpg");
      byte[] imageData = Files.readAllBytes(file.toPath());

      ProductDTO productDTO = ProductDTO.builder()
          .productSeq(10L)
          .productCd("PRD-100")
          .greenProductId("tempgreenproduct")
          .categoryCd("BCT-CTG-001")
          .productNm("추가 테스트상품1")
          .listPrice(22222)
          .productStock(222)
          .brand("testbrand")
          .productImage(imageData)  // 이미지 데이터를 바이트 배열로 직접 저장
          .build();

      productService.registerProduct(productDTO);  // 올바른 메소드 이름 사용
      log.info("Product registered: {}", productDTO);
    } catch (IOException e) {
      log.error("Error reading image file", e);
    }

  }

}
