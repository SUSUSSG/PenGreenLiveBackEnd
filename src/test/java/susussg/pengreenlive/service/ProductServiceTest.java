package susussg.pengreenlive.service;

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
  public void getProductListTest(){

    List<ProductDTO> productDTOList = productService.findAllProducts();
    log.info(productDTOList);
  }

}
