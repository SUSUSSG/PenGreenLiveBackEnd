package susussg.pengreenlive.user.controller;


import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "구매자 회원가입", description = "일반 회원 계정을 생성합니다.")
    @PostMapping("/signup")
    public ResponseEntity<?> createAccount(@RequestBody SignupFormDTO signupForm) {
        try {
            accountService.createLocalUser(signupForm);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("failed");
        }
    }

    @Operation(summary = "아이디 중복검사", description = "회원가입아이디의 사용 가능여부를 확인합니다.")
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
        }
    }

    @Operation(summary = "인증 번호 발송", description = "휴대폰번호 인증을 위한 인증코드를 발송합니다.")
    @PostMapping("/sms/request-authcode")
    public ResponseEntity<?> sendAuthCode(@RequestParam("phoneNumber") String phoneNumber) {
        log.info("/sms/request-authcode {}", phoneNumber);
        SingleMessageSentResponse response = null;

        try {
            response = accountService.sendVerificationCode(phoneNumber);
            if (response.getStatusCode().equals("2000")) {
                return ResponseEntity.ok("SMS 발송 성공.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getStatusCode());
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 에러" + response.getStatusCode());
        }
    }

    @Operation(summary = "휴대폰번호 인증 검사", description = "사용자가 입력한 인증코드의 일치 여부를 응답합니다.")
    @PostMapping("/sms/verify")
    public ResponseEntity<?> verifyCode(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("code") String code) {
        log.info("/sms/verify {} {}", phoneNumber, code);

        try {
            boolean isVerified = accountService.verifyCode(phoneNumber, code);

            if (isVerified) {
                return ResponseEntity.ok("인증 성공");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 번호가 일치하지 않습니다.");    // 401
            }
        }  catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.GONE).body(e.getMessage()); // 410
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
        }
    }
}
