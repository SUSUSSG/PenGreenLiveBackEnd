package susussg.pengreenlive.user.service;

import susussg.pengreenlive.login.dto.UserDTO;
import susussg.pengreenlive.user.dto.UpdateUserFormDTO;

public interface UserService {
    String getUserNmByUUID(String uuid);
    String getChannelNmByVendorSeq(int vendorSeq);
    String getUserAddressByUUID(String uuid);

    void updateUserInfo(UpdateUserFormDTO user);

    UpdateUserFormDTO getUserInfoByUserUUID(String userUUID);

}
