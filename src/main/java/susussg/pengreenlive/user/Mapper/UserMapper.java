package susussg.pengreenlive.user.Mapper;

import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.login.dto.UserDTO;
import susussg.pengreenlive.user.dto.SignupFormDTO;

@Mapper
public interface UserMapper {

    String selectUserNmByUUID(String uuid);
    String selectChannelNmByVendorSeq(int vendorSeq);

    void insertUser(SignupFormDTO signupForm);

    int insertLocalLogin(SignupFormDTO signupForm);

    int selectByUserId(String userId);

    UserDTO selectUserInfoByUserId(String userId);

    String selectUserAddressByUUID(String userUuid);
}
