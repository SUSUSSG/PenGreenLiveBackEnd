package susussg.pengreenlive.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.user.Mapper.UserMapper;
import susussg.pengreenlive.user.domain.LocalLogin;
import susussg.pengreenlive.user.domain.SocialLogin;
import susussg.pengreenlive.user.domain.TbUser;
import susussg.pengreenlive.user.dto.SignupFormDTO;
import susussg.pengreenlive.user.repository.UserRepository;
import susussg.pengreenlive.user.util.SmsUtil;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SmsUtil smsUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void createLocalUser(SignupFormDTO signupForm) {
        userMapper.insertUser(signupForm);
        int result = userMapper.insertLocalLogin(signupForm);
        log.info("createLocalUser {}", result);
    }

    @Transactional
    public void createSocialUser(SignupFormDTO signupForm) {
        TbUser user = modelMapper.map(signupForm, TbUser.class);
        SocialLogin socialLogin = modelMapper.map(signupForm, SocialLogin.class);
        userRepository.insertUser(user);
        userRepository.insertSocialLogin(socialLogin);
    }

    public boolean selectByUserId(String userId) {
        return userMapper.selectByUserId(userId) > 0;
    }

    public SingleMessageSentResponse sendVerificationCode(String phoneNumber) {
        String code = createVerificationCode();

        // 인증번호 저장 (1분 동안 유효)
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(phoneNumber, code, 1, TimeUnit.MINUTES);

        return smsUtil.sendOne(phoneNumber, code);
    }

    private String createVerificationCode() {
        Random rand = new Random();
        StringBuilder randomNum = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            randomNum.append(rand.nextInt(10));
        }
        return randomNum.toString();
    }

    public boolean verifyCode(String phoneNumber, String code) {
        ValueOperations<String, Object> valueOps = redisTemplate.opsForValue();
        String storedCode = (String) valueOps.get(phoneNumber);
        if (storedCode == null) {
            throw new IllegalArgumentException("인증 코드가 만료되었거나 존재하지 않습니다.");
        }
        if (storedCode.equals(code)) {
            redisTemplate.delete(phoneNumber);
            return true;
        }
        return false;
    }
}
