package susussg.pengreenlive.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import susussg.pengreenlive.login.dto.UserDTO;
import susussg.pengreenlive.user.Mapper.UserMapper;
import susussg.pengreenlive.user.dto.UpdateUserFormDTO;
import susussg.pengreenlive.util.Service.ByteArrayMultipartFile;
import susussg.pengreenlive.util.Service.ImageService;
import susussg.pengreenlive.util.Service.S3Service;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Autowired
    ImageService imageService;

    @Autowired
    S3Service s3Service;

    @Override
    public String getUserNmByUUID(String uuid) {
        return userMapper.selectUserNmByUUID(uuid); //세션의 유저 아이디값을 인자로 받음
    }

    @Override
    public String getChannelNmByVendorSeq(Long vendorSeq) {
        return userMapper.selectChannelNmByVendorSeq(vendorSeq);
    }

    @Override
    public String getUserAddressByUUID(String uuid) {
        return userMapper.selectUserAddressByUUID(uuid);
    }

    @Override
    public void updateUserInfo(UpdateUserFormDTO user) {
        if(user.getUserProfileImgFile() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(user.getUserProfileImgFile());
            byte[] compressedImage = null;

            try {
                compressedImage = imageService.compressAndResizeImage(imageBytes, 40, 0.8f);
                MultipartFile multipartFile = new ByteArrayMultipartFile(compressedImage, "profile", user.getUserUUID()+".jpg", "image/jpeg");
                log.info("multipartFile {}", multipartFile);

                String url = s3Service.uploadFile(multipartFile, "user-profile");
                log.info("url {}", url);
                user.setUserProfileImg(url);
                userMapper.updateUserInfo(user);

            } catch (IOException e) {
                log.info("log {}", e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            userMapper.updateUserInfo(user);
        }
    }

    @Override
    public UpdateUserFormDTO getUserInfoByUserUUID(String userUUID) {
        return userMapper.selectUserInfoByUserUUID(userUUID);
    }
}
