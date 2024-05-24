package susussg.pengreenlive.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.user.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/address/{uuid}")
    public ResponseEntity<?> getUserAddress(@PathVariable String uuid) {
        try {
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
}
