package susussg.pengreenlive.dashboard.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import susussg.pengreenlive.dashboard.DTO.ShopInfoDTO;
import susussg.pengreenlive.dashboard.Mapper.ShopInfoMapper;
import susussg.pengreenlive.dashboard.Service.ShopInfoService;

@Service
public class ShopInfoServiceImpl implements ShopInfoService {

  private final ShopInfoMapper shopInfoMapper;

  @Autowired
  public ShopInfoServiceImpl(ShopInfoMapper shopInfoMapper) {
    this.shopInfoMapper = shopInfoMapper;
  }

  @Override
  public ShopInfoDTO getShopInfo(Long channelSeq) {
    return shopInfoMapper.getShopInfo(channelSeq);
  }

  @Override
  @Transactional
  public void updateShopInfo(ShopInfoDTO shopInfoDTO) {
    shopInfoMapper.updateShopInfo(shopInfoDTO);
  }
}
