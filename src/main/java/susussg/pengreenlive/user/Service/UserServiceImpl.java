package susussg.pengreenlive.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.login.dto.UserDTO;
import susussg.pengreenlive.user.Mapper.UserMapper;
import susussg.pengreenlive.user.dto.UpdateUserFormDTO;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public String getUserNmByUUID(String uuid) {
        return userMapper.selectUserNmByUUID(uuid); //세션의 유저 아이디값을 인자로 받음
    }

    @Override
    public String getChannelNmByVendorSeq(int vendorSeq) {
        return userMapper.selectChannelNmByVendorSeq(vendorSeq);
    }

    @Override
    public String getUserAddressByUUID(String uuid) {
        return userMapper.selectUserAddressByUUID(uuid);
    }

    @Override
    public void updateUserInfo(UpdateUserFormDTO user) {
        userMapper.updateUserInfo(user);
    }

    @Override
    public UpdateUserFormDTO getUserInfoByUserUUID(String userUUID) {
        return userMapper.selectUserInfoByUserUUID(userUUID);
    }
}
