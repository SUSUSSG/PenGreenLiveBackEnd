package susussg.pengreenlive.dashboard.Controller;

import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import susussg.pengreenlive.dashboard.DTO.ShopInfoDTO;
import susussg.pengreenlive.dashboard.Service.ShopInfoService;

@RestController
@Log4j2
public class ShopInfoController {

  @Autowired
  private final ShopInfoService shopInfoService;

  public ShopInfoController(ShopInfoService shopInfoService) {
    this.shopInfoService = shopInfoService;
  }

  @GetMapping("/shop/{channelSeq}")
  public ResponseEntity<?> getShopInfo(@PathVariable Long channelSeq) {
    try {
      ShopInfoDTO shopInfo = shopInfoService.getShopInfo(channelSeq);
      if (shopInfo != null) {
        return ResponseEntity.ok(shopInfo);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("상점 정보 검색 중 오류 발생: " + e.getMessage());
    }
  }

  @PutMapping("/shop/{channelSeq}")
  public ResponseEntity<?> updateShopInfo(@PathVariable Long channelSeq,
      @RequestParam("nickname") String nickname,
      @RequestParam("shoplink") String shoplink,
      @RequestParam("description") String description,
      @RequestParam(value = "image", required = false) MultipartFile image) {

    ShopInfoDTO existingShopInfo = shopInfoService.getShopInfo(channelSeq);
    if (existingShopInfo == null) {
      return ResponseEntity.notFound().build();
    }

    if (image != null && !image.isEmpty()) {
      try {
        byte[] imageBytes = image.getBytes();
        existingShopInfo.setChannelImage(imageBytes);
      } catch (IOException e) {
        return ResponseEntity.badRequest().body("이미지 파일 처리 중 오류 발생: " + e.getMessage());
      }
    }

    existingShopInfo.setChannelNM(nickname);
    existingShopInfo.setChannelUrl(shoplink);
    existingShopInfo.setChannelInfo(description);

    shopInfoService.updateShopInfo(existingShopInfo);
    return ResponseEntity.ok().body("Shop info updated successfully.");
  }

}
