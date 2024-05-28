package susussg.pengreenlive.vendor.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import susussg.pengreenlive.login.dto.VendorDTO;
import susussg.pengreenlive.vendor.dto.VendorSignupFormDTO;

@Mapper
public interface VendorMapper {
    VendorDTO selectVendorInfoByBusinessId(String businessId);

    void insertVendor(VendorSignupFormDTO vendorSignupForm);

    void insertChannel(@Param("vendorSeq") Long vendorSeq);

    void updateChannelSeq(@Param("vendorSeq") Long vendorSeq);

}
