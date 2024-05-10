package susussg.pengreenlive.dashboard.Mapper;

import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.dashboard.DTO.ShopInfoDTO;

@Mapper
public interface ShopInfoMapper {

  void insertShopInfo(ShopInfoDTO shopInfoDTO);

}
