package susussg.pengreenlive.user.service;

public interface UserService {

    String getUserNmByUUID(String uuid);

    String getChannelNmByVendorSeq(int vendorSeq);

    String getUserAddressByUUID(String uuid);
}
