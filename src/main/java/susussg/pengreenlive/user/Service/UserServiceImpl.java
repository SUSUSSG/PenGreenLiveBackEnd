package susussg.pengreenlive.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.user.Mapper.UserMapper;

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
}
