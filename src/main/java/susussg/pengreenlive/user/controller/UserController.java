package susussg.pengreenlive.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.login.dto.Member;
import susussg.pengreenlive.login.dto.UserDTO;
import susussg.pengreenlive.login.service.SecurityService;
import susussg.pengreenlive.user.dto.UpdateUserFormDTO;
import susussg.pengreenlive.user.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/address")
    public ResponseEntity<?> getUserAddress() {

        try {
            String uuid = securityService.getCurrentUserUuid();
            String address = userService.getUserAddressByUUID(uuid);
            log.info("/address {}", address);
            if (address != null) {
                return ResponseEntity.ok().body(address);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("주소 없음");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    @PatchMapping
    public ResponseEntity<?> updateUserProfile(@RequestBody UpdateUserFormDTO user) {
        log.info("/update {}", user);

        try {
            String uuid = securityService.getCurrentUserUuid();
            user.setUserUUID(uuid);
            userService.updateUserInfo(user);

            return ResponseEntity.ok().body("success");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    @GetMapping
    public ResponseEntity<?> updateUserProfile() {

        try {
            String uuid = securityService.getCurrentUserUuid();
            UpdateUserFormDTO user = userService.getUserInfoByUserUUID(uuid);

            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }

    }
}
