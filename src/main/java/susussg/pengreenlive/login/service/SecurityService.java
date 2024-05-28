package susussg.pengreenlive.login.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.login.dto.Member;
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
        if (isNumeric(username)) {
            VendorDTO vendor = vendorMapper.selectVendorInfoByBusinessId(username);
            log.info("vendor login = {}", vendor);

            if (vendor != null) {
                return buildVendorDetails(vendor);
            }
        } else {
            UserDTO user = userMapper.selectUserInfoByUserId(username);
            log.info("user login = {}", user);

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
            return new Member(user.getUserId(), user.getUserPw(), authorities, user.getUserNm(), user.getUserUuid(), null, null);
        }
        authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        return new Member(user.getUserId(), user.getUserPw(), authorities, user.getUserNm(), user.getUserUuid(), null, null);
    }

    private UserDetails buildVendorDetails(VendorDTO vendor) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        log.info("vendor info {}", vendor);
        authorities.add(new SimpleGrantedAuthority(MemberRole.VENDOR.getValue()));
        return new Member(vendor.getBusinessId(), vendor.getVendorPw(), authorities, vendor.getVendorNm(),
                null, vendor.getVendorSeq(), vendor.getChannelSeq());
    }

    private boolean isNumeric(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

    public String getCurrentUserUuid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Member member = (Member) authentication.getPrincipal();
        return member.getUserUuid();
    }

    public Long getCurrentVendorSeq() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Member member = (Member) authentication.getPrincipal();
        return member.getVendorSeq();
    }

    public Long getCurrentChannelSeq() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Member member = (Member) authentication.getPrincipal();
        return member.getChannelSeq();
    }

    public String getCurrentUserNm() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Member member = (Member) authentication.getPrincipal();
        return member.getUserNm();
    }

    public Authentication convertJsonToAuthentication(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, UsernamePasswordAuthenticationToken.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON to Authentication object", e);
        }
    }
}
