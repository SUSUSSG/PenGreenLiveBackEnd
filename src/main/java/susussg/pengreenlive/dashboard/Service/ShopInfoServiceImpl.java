package susussg.pengreenlive.dashboard.Service;

import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import susussg.pengreenlive.dashboard.DTO.ShopInfoDTO;
import susussg.pengreenlive.dashboard.Mapper.ShopInfoMapper;
import susussg.pengreenlive.util.Service.ByteArrayMultipartFile;
import susussg.pengreenlive.util.Service.ImageService;
import susussg.pengreenlive.util.Service.S3Service;

@Service
public class ShopInfoServiceImpl implements ShopInfoService {

  private final ShopInfoMapper shopInfoMapper;

  @Autowired
  ImageService imageService;

  @Autowired
  S3Service s3Service;


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

    if(shopInfoDTO.getChannelImage() != null) {
      byte[] imageBytes = Base64.getDecoder().decode(shopInfoDTO.getChannelImage());
      byte[] compressedImage = null;
      try {
        compressedImage = imageService.compressAndResizeImage(imageBytes, 400, 1f);
        MultipartFile multipartFile = new ByteArrayMultipartFile(compressedImage, "channel", "channel.jpg", "image/jpeg");
        String url = s3Service.uploadFile(multipartFile, "channel");
        shopInfoDTO.setRawchannelImage(url);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    }

    shopInfoMapper.updateShopInfo(shopInfoDTO);
  }
}
