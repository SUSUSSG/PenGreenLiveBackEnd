package susussg.pengreenlive.vendor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import susussg.pengreenlive.user.Mapper.UserMapper;
import susussg.pengreenlive.user.dto.UpdateUserFormDTO;
import susussg.pengreenlive.util.Service.ByteArrayMultipartFile;
import susussg.pengreenlive.util.Service.ImageService;
import susussg.pengreenlive.util.Service.S3Service;
import susussg.pengreenlive.vendor.mapper.VendorMapper;

import java.io.IOException;
import java.util.Base64;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    VendorMapper vendorMapper;


    @Override
    public Long getChannelSeqByVendorSeq(Long vendorSeq) {
        return vendorMapper.selectChannelSeqByVendorSeq(vendorSeq);
    }
//
//    @Override
//    public String getChannelNmByVendorSeq(int vendorSeq) {
//        return userMapper.selectChannelNmByVendorSeq(vendorSeq);
//    }
//
//    @Override
//    public String getUserAddressByUUID(String uuid) {
//        return userMapper.selectUserAddressByUUID(uuid);
//    }
//
//    @Override
//    public void updateUserInfo(UpdateUserFormDTO user) {
//        if(user.getUserProfileImg() != null) {
//            byte[] imageBytes = Base64.getDecoder().decode(user.getUserProfileImg());
//            byte[] compressedImage = null;
//            try {
//                compressedImage = imageService.compressAndResizeImage(imageBytes, 400, 1f);
//                MultipartFile multipartFile = new ByteArrayMultipartFile(compressedImage, "profile", "profile.jpg", "image/jpeg");
//                String url = s3Service.uploadFile(multipartFile, "user-profile");
//                user.setUserProfileImg(url);
//                userMapper.updateUserInfo(user);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    @Override
//    public UpdateUserFormDTO getUserInfoByUserUUID(String userUUID) {
//        return userMapper.selectUserInfoByUserUUID(userUUID);
//    }
}
