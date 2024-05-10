package susussg.pengreenlive.dashboard.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

  @PutMapping("/update/{channelSeq}")
  public ResponseEntity<?> updateShopInfo(@PathVariable Long channelSeq, @RequestBody ShopInfoDTO shopInfoDTO) {
    try {
      // 채널 ID를 DTO에 설정
      shopInfoDTO.setChannelSeq(channelSeq);
      shopInfoService.updateShopInfo(shopInfoDTO);
      return ResponseEntity.ok().body("Shop info updated successfully.");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error updating shop info: " + e.getMessage());
    }
  }
}
