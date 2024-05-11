package susussg.pengreenlive.dashboard.Service;

import susussg.pengreenlive.dashboard.DTO.ShopInfoDTO;

public interface ShopInfoService {

  ShopInfoDTO getShopInfo(Long channelSeq);

  void updateShopInfo(ShopInfoDTO shopInfoDTO);

}
