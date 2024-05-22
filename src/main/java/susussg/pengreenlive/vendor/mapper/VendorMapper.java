package susussg.pengreenlive.vendor.mapper;

import org.apache.ibatis.annotations.Mapper;
import susussg.pengreenlive.login.dto.VendorDTO;

@Mapper
public interface VendorMapper {
    VendorDTO selectVendorInfoByBusinessId(String businessId);
}
