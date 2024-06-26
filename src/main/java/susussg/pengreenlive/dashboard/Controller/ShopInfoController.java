package susussg.pengreenlive.dashboard.Controller;

import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import susussg.pengreenlive.dashboard.DTO.ShopInfoDTO;
import susussg.pengreenlive.dashboard.Service.ShopInfoService;
import susussg.pengreenlive.login.service.SecurityService;

@RestController
@Log4j2
@RequestMapping("/api")
public class ShopInfoController {

  @Autowired
  private final ShopInfoService shopInfoService;

  @Autowired
  private SecurityService securityService;

  public ShopInfoController(ShopInfoService shopInfoService) {
    this.shopInfoService = shopInfoService;
  }

  @Operation(summary = "상점 정보 조회", description = "상점 정보를 조회합니다.")
  @GetMapping("/shop")
  public ResponseEntity<?> getShopInfo() {
    Long channelSeq = securityService.getCurrentChannelSeq();
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

  @Operation(summary = "상점 정보 수정", description = "상점 정보를 수정합니다.")
  @PutMapping("/shop")
  public ResponseEntity<String> updateShopInfo(@RequestBody ShopInfoDTO shopInfoDTO) {

    Long channelSeq = securityService.getCurrentChannelSeq();
    shopInfoDTO.setChannelSeq(channelSeq);
    shopInfoService.updateShopInfo(shopInfoDTO);
    return ResponseEntity.ok().body("Shop info updated successfully.");
  }

}
