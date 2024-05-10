package susussg.pengreenlive.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.dashboard.DTO.ShopInfoDTO;
import susussg.pengreenlive.dashboard.Service.ShopInfoService;

@SpringBootTest
@Log4j2
public class ShopInfoServiceTest {

  @Autowired
  private final ShopInfoService shopInfoService;

  @Autowired
  public ShopInfoServiceTest(ShopInfoService shopInfoService) {
    this.shopInfoService = shopInfoService;
  }

  @Test
  public void testSaveShopInfo() throws IOException {

    File file = new File("C:/Users/kuy06/OneDrive/Desktop/c2.jpg");
    byte[] imageData = Files.readAllBytes(file.toPath());

    ShopInfoDTO shopInfoDTO = new ShopInfoDTO();
    shopInfoDTO.setChannelNM("TestShop2");
    shopInfoDTO.setChannelUrl("www.test2.com");
    shopInfoDTO.setChannelImage(imageData);
    shopInfoDTO.setChannelInfo("infotest2");

    shopInfoService.saveShopInfo(shopInfoDTO);

    log.info("Shop info saved successfully.");

  }
}
