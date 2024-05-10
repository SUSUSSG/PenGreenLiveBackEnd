package susussg.pengreenlive.dashboard.Controller;

import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import susussg.pengreenlive.dashboard.DTO.ShopInfoDTO;
import susussg.pengreenlive.dashboard.Service.ShopInfoService;

@RestController
@Log4j2
public class ShopInfoController {

  private final ShopInfoService shopInfoService;

  @Autowired
  public ShopInfoController(ShopInfoService shopInfoService) {
    this.shopInfoService = shopInfoService;
  }

  @PostMapping("/shop-modify")
  public ResponseEntity<Void> addShopInfo(

      @RequestParam("channelSeq") Long channelSeq,
      @RequestParam("channelNM") String channelNM,
      @RequestParam("channelUrl") String channelUrl,
      @RequestParam("channelImage") MultipartFile channelImage,
      @RequestParam("channelInfo") String channelInfo) throws IOException {

    ShopInfoDTO shopInfoDTO = ShopInfoDTO.builder()
        .channelSeq(channelSeq)
        .channelNM(channelNM)
        .channelUrl(channelUrl)
        .channelImage(channelImage.getBytes())
        .channelInfo(channelInfo)
        .build();

    shopInfoService.saveShopInfo(shopInfoDTO);
    return new ResponseEntity<>(HttpStatus.CREATED);

  }
}
