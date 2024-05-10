package susussg.pengreenlive.dashboard.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

//  @PutMapping("/update")
//  public ResponseEntity<?> updateShopInfo(@RequestBody ShopInfoDTO shopInfoDTO) {
//    try {
//      shopInfoService.updateShopInfo(shopInfoDTO);
//      return ResponseEntity.ok().body("Shop info updated successfully.");
//    } catch (Exception e) {
//      return ResponseEntity.badRequest().body("Error updating shop info: " + e.getMessage());
//    }
//  }
}
