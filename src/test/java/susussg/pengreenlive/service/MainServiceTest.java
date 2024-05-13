package susussg.pengreenlive.service;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import susussg.pengreenlive.main.Service.MainService;
import susussg.pengreenlive.util.DTO.ForbiddenWordDTO;
import susussg.pengreenlive.util.Service.ForbiddenWordService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log4j2
public class MainServiceTest {
    @Autowired
    MainService mainService;


    @Test
    public void getMainCarouselsTest() {

        log.info("getMainCarousel list : " + mainService.getMainCarousels());
    }
}
