package susussg.pengreenlive.service;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.user.service.UserService;
import susussg.pengreenlive.util.DTO.BanwordValidationResultDTO;

@Log4j2
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void testGetUserNmByUUID() {
        String testUUID = "a1b2c3d4-e5f6-g7h8-i9j0-k1l2m3n4o5p6";

        String userNm = userService.getUserNmByUUID(testUUID);
        log.info(userNm);
    }
    @Test
    public void testGetChannelNmByVendorSeq() {
        Long vendorSeq = 1L;

        String channelNm = userService.getChannelNmByVendorSeq(vendorSeq);
        log.info(channelNm);
    }
}
