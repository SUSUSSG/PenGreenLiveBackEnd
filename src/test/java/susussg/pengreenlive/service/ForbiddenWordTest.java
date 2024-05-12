package susussg.pengreenlive.service;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import susussg.pengreenlive.util.DTO.ForbiddenWordDTO;
import susussg.pengreenlive.util.Service.ForbiddenWordService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log4j2
public class ForbiddenWordTest {

    @Autowired
    ForbiddenWordService forbiddenWordService;


    @Test
    public void getDefaultForbiddenWordListTest() {
        List<ForbiddenWordDTO> forbiddenWordList = forbiddenWordService.getDefaultForbiddenWordList();
        log.info("Default forbiddenword list : " + forbiddenWordList);
    }
    @Test
    public void getIndividualForbiddenWordListTest() {
        int broadcastSeq = 6;
        List<ForbiddenWordDTO> forbiddenWordList = forbiddenWordService.getIndividualForbiddenWordList(broadcastSeq);
        log.info("Individual forbiddenword list : " + forbiddenWordList);
    }
}
