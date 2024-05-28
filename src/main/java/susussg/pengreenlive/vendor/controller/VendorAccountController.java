package susussg.pengreenlive.vendor.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.user.dto.SignupFormDTO;
import susussg.pengreenlive.user.service.AccountService;
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
}
