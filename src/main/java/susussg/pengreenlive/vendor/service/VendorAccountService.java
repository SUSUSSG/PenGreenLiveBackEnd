package susussg.pengreenlive.vendor.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.login.dto.Member;
import susussg.pengreenlive.user.Mapper.UserMapper;
import susussg.pengreenlive.user.domain.SocialLogin;
import susussg.pengreenlive.user.domain.TbUser;
import susussg.pengreenlive.user.dto.SignupFormDTO;
import susussg.pengreenlive.user.repository.UserRepository;
import susussg.pengreenlive.user.util.SmsUtil;
import susussg.pengreenlive.vendor.dto.VendorSignupFormDTO;
import susussg.pengreenlive.vendor.mapper.VendorMapper;

import java.util.Random;
import java.util.concurrent.TimeUnit;


@Slf4j
@RequiredArgsConstructor
@Service
public class VendorAccountService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    VendorMapper vendorMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;



    public void createVendor(VendorSignupFormDTO signupForm) {
        log.info("service {}", signupForm);

        try {
            signupForm.setVendorPw(passwordEncoder.encode(signupForm.getVendorPw()));
            vendorMapper.insertVendor(signupForm);
            Long vendorSeq = signupForm.getVendorSeq();
            vendorMapper.updateChannelSeq(vendorSeq);
            vendorMapper.insertChannel(vendorSeq);
        } catch (Exception e) {
            log.info("error {}", e.getMessage());
        }
    }


    public Member selectByBusinessId(String businessId) {
        return null;
    }

}
