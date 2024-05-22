package susussg.pengreenlive.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.user.dto.SignupFormDTO;
import susussg.pengreenlive.user.service.AccountService;

import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<?> createAccount(@RequestBody SignupFormDTO signupForm) {

        log.info("signup form {}", signupForm);
        try {
            accountService.createLocalUser(signupForm);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("failed");
        }
    }

    @PostMapping("/check-id")
    public ResponseEntity<?> checkDuplicateUserId(@RequestBody Map<String, String> form) {

        String userId = form.get("id");
        log.info("checj-id {}", userId);
        try {
            boolean isDuplicate = accountService.selectByUserId(userId);
            if (!isDuplicate)
                return ResponseEntity.ok().body("available");
            else
                return ResponseEntity.ok().body("duplicate");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("failed");
        }
    }

    @PostMapping("/request-authcode")
    public ResponseEntity<?> sendPhoneVerificationCode(@RequestBody Map<String, String> form) {
        String phoneNumber = form.get("phoneNumber");
        log.info("/request-authcode {}", phoneNumber);
        try {
            return ResponseEntity.ok().body("available");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("failed");
        }
    }
}
