package susussg.pengreenlive.vendor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.login.service.SecurityService;
import susussg.pengreenlive.user.dto.UpdateUserFormDTO;
import susussg.pengreenlive.user.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class VendorController {
    private final UserService userService;

    @Autowired
    private SecurityService securityService;

}
