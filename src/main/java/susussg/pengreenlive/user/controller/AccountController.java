package susussg.pengreenlive.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import susussg.pengreenlive.user.dto.SignupFormDTO;
import susussg.pengreenlive.user.service.AccountService;
import org.springframework.beans.factory.annotation.Value;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
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

    @PostMapping("/sms/request-authcode")
    public ResponseEntity<?> sendAuthCode(@RequestParam String phoneNumber) {
        log.info("/sms/request-authcode {}", phoneNumber);

        try {
            SingleMessageSentResponse response = accountService.sendVerificationCode(phoneNumber);
            if (response.getStatusCode().equals("2000")) {
                return ResponseEntity.ok("SMS 발송 성공.");
            } else {
                return ResponseEntity.status(500).body("SMS 발송 실패.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
    }

    @PostMapping("/sms/verify")
    public ResponseEntity<?> verifyCode(@RequestParam String phoneNumber, @RequestParam String code) {
        log.info("/sms/verify {} {}", phoneNumber, code);

        try {
            boolean isVerified = accountService.verifyCode(phoneNumber, code);

            if (isVerified) {
                return ResponseEntity.ok("인증 성공.");
            } else {
                return ResponseEntity.status(400).body("잘못된 인증번호.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
        }
     }
}
