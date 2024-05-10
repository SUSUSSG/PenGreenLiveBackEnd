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
  @CrossOrigin(origins = "http://localhost:5173")
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

  @PutMapping("/shop-modify/{channelSeq}")
  @CrossOrigin(origins = "http://localhost:5173")
  public ResponseEntity<?> updateShopInfo(@PathVariable Long channelSeq,
      @RequestParam("nickname") String nickname,
      @RequestParam("shoplink") String shoplink,
      @RequestParam("description") String description,
      @RequestParam("image") MultipartFile image) {
    try {
      ShopInfoDTO shopInfoDTO = ShopInfoDTO.builder()
          .channelSeq(channelSeq)
          .channelNM(nickname)
          .channelUrl(shoplink)
          .channelInfo(description)
          .build();

      if (!image.isEmpty()) {
        byte[] imageBytes = image.getBytes();
        shopInfoDTO.setChannelImage(imageBytes); // 이미지를 byte[] 형태로 DTO에 설정
      }

      shopInfoService.updateShopInfo(shopInfoDTO);
      return ResponseEntity.ok().body("Shop info updated successfully.");
    } catch (IOException e) {
      return ResponseEntity.badRequest().body("이미지 파일 처리 중 오류 발생: " + e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error updating shop info: " + e.getMessage());
    }
  }
}
