package susussg.pengreenlive.vendor.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.vendor.dto.VendorSignupFormDTO;
import susussg.pengreenlive.vendor.service.VendorAccountService;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/vendor")
public class VendorAccountController {

    private final VendorAccountService accountService;

    @Operation(summary = "판매자 회원가입", description = "판매자의 계정 정보를 생성합니다.")
    @PostMapping("/signup")
    public ResponseEntity<?> createVendorAccount(@RequestBody VendorSignupFormDTO vendorSignupForm) {
        log.info("signup form {}", vendorSignupForm);
        try {
            accountService.createVendor(vendorSignupForm);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            log.error("Error creating vendor account", e);
            return ResponseEntity.internalServerError().body("failed");
        }
    }

    @Operation(summary = "사업자번호 검증", description = "판매자의 사업자번호가 유효한지 검증합니다.")
    @PostMapping("/check-business-number")
    public ResponseEntity<?> checkBusinessNumber(@RequestBody Map<String, String> request) {
        String businessNumber = request.get("businessNumber");
        log.info("Checking business number: {}", businessNumber);
        try {
            Map<String, Object> validationResponse = accountService.validateBusinessNumber(businessNumber);
            log.info("Validation response: {}", validationResponse);
            return ResponseEntity.ok().body(validationResponse);
        } catch (Exception e) {
            log.error("Business number validation failed", e);
            return ResponseEntity.status(500).body("조회 실패");
        }
    }
}
