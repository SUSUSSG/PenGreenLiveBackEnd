package susussg.pengreenlive.login.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.user.service.UserService;

import static org.junit.Assert.*;

@Log4j2
@SpringBootTest
public class SecurityServiceTest {

    @Autowired
    SecurityService securityService;

    @Test
    public void testGetUserNmByUUID() {
        String testUUID = "seoy316";

        securityService.loadUserByUsername(testUUID);
    }

    @Test
    public void isNumeric() {
        String str = "seoy316";
        log.info("result {}", str.chars().allMatch(Character::isDigit));
    }
}