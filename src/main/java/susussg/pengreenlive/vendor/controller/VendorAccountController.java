package susussg.pengreenlive.vendor.controller;

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

//    @PostMapping("/check-id")
//    public ResponseEntity<?> checkDuplicateUserId(@RequestBody Map<String, String> form) {
//
//        String userId = form.get("id");
//        log.info("checj-id {}", userId);
//        try {
//            boolean isDuplicate = accountService.selectByUserId(userId);
//            if (!isDuplicate)
//                return ResponseEntity.ok().body("available");
//            else
//                return ResponseEntity.ok().body("duplicate");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed");
//        }
//    }
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
