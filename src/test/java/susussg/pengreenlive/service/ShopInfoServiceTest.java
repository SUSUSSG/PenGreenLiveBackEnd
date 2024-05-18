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
  public void getShopInfoTest() {

    Long channelSeq = 1L;
    ShopInfoDTO result = shopInfoService.getShopInfo(channelSeq);

    if (result != null) {
      log.info("상점 정보 조회 성공. Retrieved data: {}", result);
    } else {
      log.error("상점 정보 조회 실패: {}", channelSeq);
    }

  }

//  @Test
//  public void testUpdateShopInfo() throws IOException {
//
//    File file = new File("C:/Users/kuy06/OneDrive/Desktop/c2.jpg");
//    byte[] imageData = Files.readAllBytes(file.toPath());
//
//    ShopInfoDTO shopInfoDTO = new ShopInfoDTO();
//    shopInfoDTO.setChannelSeq(1L);
//    shopInfoDTO.setChannelNM("UpdatedTestShop");
//    shopInfoDTO.setChannelUrl("www.updatedtest.com");
//    shopInfoDTO.setChannelImage(imageData);
//    shopInfoDTO.setChannelInfo("Updated info");
//
//    shopInfoService.updateShopInfo(shopInfoDTO);
//
//    log.info("상점정보 변경 성공");
//  }

}
