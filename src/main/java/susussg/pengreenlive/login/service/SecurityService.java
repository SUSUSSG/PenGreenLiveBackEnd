package susussg.pengreenlive.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.login.dto.UserDTO;
import susussg.pengreenlive.login.dto.VendorDTO;
import susussg.pengreenlive.login.role.MemberRole;
import susussg.pengreenlive.user.Mapper.UserMapper;
import susussg.pengreenlive.vendor.mapper.VendorMapper;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class SecurityService implements UserDetailsService {
    private final UserMapper userMapper;
    private final VendorMapper vendorMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username = {}", username);
        if (isNumeric(username)) {
            VendorDTO vendor = vendorMapper.selectVendorInfoByBusinessId(username);
            log.info("vendor = {}", vendor);

            if (vendor != null) {
                return buildVendorDetails(vendor);
            }
        } else {
            UserDTO user = userMapper.selectUserInfoByUserId(username);
            log.info("user = {}", user);

            if (user != null) {
                return buildUserDetails(user);
            }
        }

        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다. " + username);
    }

    private UserDetails buildUserDetails(UserDTO user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!user.getAccountActive()) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.DEACTIVE.getValue()));
            return new User(user.getUserId(), user.getUserPw(), authorities);
        }
        authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        return new User(user.getUserId(), user.getUserPw(), authorities);
    }

    private UserDetails buildVendorDetails(VendorDTO vendor) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(MemberRole.VENDOR.getValue()));
        return new User(vendor.getBusinessId(), vendor.getVendorPw(), authorities);
    }

    private boolean isNumeric(String str) {
        return str.chars().allMatch(Character::isDigit);
    }
}
