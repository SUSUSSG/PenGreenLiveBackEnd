package susussg.pengreenlive.user.controller;

import io.swagger.v3.oas.annotations.Operation;
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

import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    private SecurityService securityService;

    @Operation(summary = "회원 주소 정보", description = "주문 페이지 접속 시, 회원이 등록해놓은 배송 주소를 응답합니다.")
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

    @Operation(summary = "회원정보 조회", description = "회원이 자신의 정보를 조회합니다.")
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

    @Operation(summary = "회원정보 수정", description = "회원 정보를 수정합니다.")
    @PatchMapping
    public ResponseEntity<?> updateUserProfile(@RequestBody UpdateUserFormDTO user) {
        try {
            String uuid = securityService.getCurrentUserUuid();
            user.setUserUUID(uuid);
            userService.updateUserInfo(user);
            return ResponseEntity.ok().body("success");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }
}
