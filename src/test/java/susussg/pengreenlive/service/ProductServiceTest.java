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
  public void getProductCategoryListTest() {

    List<ProductDTO> productCategoryDTOList = productService.getAllCategoryCodes();
    log.info("ssssss" + productCategoryDTOList);
  }

  @Test
  public void addProductTest() throws IOException {

    String category="BCT-CTG-003";

    try {
      File file = new File("C:/Users/kuy06/OneDrive/Desktop/c2.jpg");
      log.info(file);
      byte[] imageData = Files.readAllBytes(file.toPath());

      ProductDTO productDTO = ProductDTO.builder()
          .productSeq(13L)
          .productCd("PRD-100")
          .greenProductId("tempgreenproduct")
          .categoryCd(category)
          .productNm("추가 테스트상품1")
          .listPrice(22222)
          .productStock(222)
          .brand("testbrand")
          .productImage(imageData)
          .build();

      productService.registerProduct(productDTO);
      log.info("Product registered: {}", productDTO);
    } catch (IOException e) {
      log.error("Error reading image file", e);
    }

  }

}
