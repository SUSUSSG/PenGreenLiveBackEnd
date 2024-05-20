package susussg.pengreenlive.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.user.Mapper.UserMapper;
import susussg.pengreenlive.user.domain.LocalLogin;
import susussg.pengreenlive.user.domain.SocialLogin;
import susussg.pengreenlive.user.domain.TbUser;
import susussg.pengreenlive.user.dto.SignupFormDTO;
import susussg.pengreenlive.user.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    UserMapper userMapper;

    @Transactional
    public void createLocalUser(SignupFormDTO signupForm) {
        userMapper.insertUser(signupForm);
        int result = userMapper.insertLocalLogin(signupForm);
        log.info("createLocalUser {}", result);
    }

//    @Transactional
////    public void createLocalUser(SignupFormDTO signupForm) {
////        TbUser user = modelMapper.map(signupForm, TbUser.class);
////        userRepository.insertUser(user);
////        String uuid = user.getUserUuid();
////        signupForm.setUserUuid(uuid);
////        LocalLogin localLogin = modelMapper.map(signupForm, LocalLogin.class);
////        userRepository.insertLocalLogin(localLogin);
////    }

    @Transactional
    public void createSocialUser(SignupFormDTO signupForm) {
        TbUser user = modelMapper.map(signupForm, TbUser.class);
        SocialLogin socialLogin = modelMapper.map(signupForm, SocialLogin.class);
        userRepository.insertUser(user);
        userRepository.insertSocialLogin(socialLogin);
    }
}
