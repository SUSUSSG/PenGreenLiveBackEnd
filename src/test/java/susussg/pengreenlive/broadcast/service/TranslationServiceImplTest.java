package susussg.pengreenlive.broadcast.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Log4j2
public class TranslationServiceImplTest {
    @Autowired
    private TranslationService translationService;

    @Test
    public void transalateTest(){
        log.info(translationService.translateKoreanToEnglish("한글입니다."));
    }
}