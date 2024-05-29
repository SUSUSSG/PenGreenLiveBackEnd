package susussg.pengreenlive.dashboard.Service;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import susussg.pengreenlive.dashboard.DTO.GreenLabelDTO;
import susussg.pengreenlive.dashboard.DTO.ProductDTO;
import susussg.pengreenlive.dashboard.DTO.ProductUpdateDTO;
import susussg.pengreenlive.dashboard.DTO.VendorProductListDTO;
import susussg.pengreenlive.dashboard.Mapper.ProductMapper;
import susussg.pengreenlive.util.Service.ByteArrayMultipartFile;
import susussg.pengreenlive.util.Service.ImageService;
import susussg.pengreenlive.util.Service.S3Service;
import susussg.pengreenlive.util.Service.DateUtil;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductMapper productMapper;

  @Autowired
  ImageService imageService;

  @Autowired
  S3Service s3Service;

  @Autowired
  public ProductServiceImpl(ProductMapper productMapper) {
    this.productMapper = productMapper;
  }

  @Override
  public List<ProductDTO> findAllProducts() {
    return productMapper.findAllProducts();
  }

  @Override
  @Transactional
  public boolean registerProduct(ProductDTO productDTO, Long vendorSeq, Long channelSeq) {
    try {
      if (productDTO.getBase64Image() != null) {
        byte[] imageBytes = Base64.getDecoder().decode(productDTO.getBase64Image());

        byte[] compressedImage = imageService.compressAndResizeImage(imageBytes, 400, 1f);
        MultipartFile multipartFile = new ByteArrayMultipartFile(compressedImage, "productImage",
            "product.jpg", "image/jpeg");
        String url = s3Service.uploadFile(multipartFile, "product");

        productDTO.setBase64Image(url);
      }
      productMapper.insertProduct(productDTO);
      Long productSeq = productDTO.getProductSeq();
      productMapper.insertProductStock(productSeq, productDTO.getProductStock());
      productMapper.insertChannelSalesProduct(productSeq, vendorSeq, channelSeq);

      Long labelIdSeq = productDTO.getLabelIdSeq();
      GreenLabelDTO greenLabelDTO = new GreenLabelDTO();
      greenLabelDTO.setProductSeq(productDTO.getProductSeq());
      greenLabelDTO.setCertificationReason(productDTO.getCertificationReason());

      // 날짜 문자열을 LocalDateTime으로 변환하여 설정
      greenLabelDTO.setCertificationExpirationDate(
          DateUtil.parseToLocalDateTime(productDTO.getCertificationExpirationDate()));

      // 라벨 시퀀스 값에 따른 분기 처리
      switch (labelIdSeq.intValue()) {
        case 4:
          for (Long seq : new Long[]{1L, 2L}) {
            greenLabelDTO.setLabelIdSeq(seq);
            productMapper.insertProductGreenLabel(greenLabelDTO);
          }
          break;
        case 5:
          for (Long seq : new Long[]{1L, 2L, 3L}) {
            greenLabelDTO.setLabelIdSeq(seq);
            productMapper.insertProductGreenLabel(greenLabelDTO);
          }
          break;
        case 6:
          for (Long seq : new Long[]{1L, 3L}) {
            greenLabelDTO.setLabelIdSeq(seq);
            productMapper.insertProductGreenLabel(greenLabelDTO);
          }
          break;
        case 7:
          for (Long seq : new Long[]{2L, 3L}) {
            greenLabelDTO.setLabelIdSeq(seq);
            productMapper.insertProductGreenLabel(greenLabelDTO);
          }
          break;
        default:
          greenLabelDTO.setLabelIdSeq(labelIdSeq);
          productMapper.insertProductGreenLabel(greenLabelDTO);
          break;
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Product registration failed", e);
    }
  }

  @Override
  public List<ProductDTO> getAllCategoryCodes() {
    return productMapper.selectAllCategoryCodes();
  }

  @Override
  public List<VendorProductListDTO> findProductsByVendor(Long vendorSeq) {
    return productMapper.findProductsByVendor(vendorSeq);
  }

  @Override
  public void updateProduct(Long productSeq, ProductUpdateDTO productUpdateDTO) {
    try {
      // 이미지가 존재하고 수정된 경우에만 새로운 이미지 업로드 수행
      if (productUpdateDTO.getBase64Image() != null && !productUpdateDTO.getBase64Image().isEmpty()) {
        byte[] imageBytes = Base64.getDecoder().decode(productUpdateDTO.getBase64Image());

        byte[] compressedImage = imageService.compressAndResizeImage(imageBytes, 400, 1f);
        MultipartFile multipartFile = new ByteArrayMultipartFile(compressedImage, "productImage", "product.jpg", "image/jpeg");
        String url = s3Service.uploadFile(multipartFile, "product");

        productUpdateDTO.setProductImage(url);
      }

      // 이미지가 수정되지 않은 경우 기존 이미지 URL을 유지
      if (productUpdateDTO.getBase64Image() == null || productUpdateDTO.getBase64Image().isEmpty()) {
        // 이전에 저장된 제품 정보를 가져와서 productImage 값을 설정
        ProductUpdateDTO existingProduct = productMapper.getProductById(productSeq);
        productUpdateDTO.setProductImage(existingProduct.getProductImage());
      }

      productUpdateDTO.setProductSeq(productSeq);
      productMapper.updateProduct(productUpdateDTO);
      productMapper.updateProductStock(productSeq, productUpdateDTO.getProductStock());
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Product update failed", e);
    }
  }


  @Override
  public void deleteProduct(Long vendorSeq, Long productSeq) {
    try {
      productMapper.deleteChannelSalesProduct(vendorSeq, productSeq);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Product deletion from vendor's sales channel failed", e);
    }
  }

  @Override
  public List<ProductDTO> getAllProductsWithLabels(Long vendorSeq) {
    return productMapper.selectProductWithLabelsByVendor(vendorSeq);
  }
}
