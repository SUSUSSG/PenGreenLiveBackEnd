package susussg.pengreenlive.util.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import org.ahocorasick.trie.PayloadEmit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import lombok.extern.log4j.Log4j2;
import susussg.pengreenlive.util.DTO.BanwordDTO;
import susussg.pengreenlive.util.DTO.BanwordValidationResultDTO;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log4j2
public class BanwordServiceTest {

    @Autowired
    private BanwordService banwordService;

    @BeforeEach
    public void setup() {

        banwordService.init();
    }

    @Test
    public void testValidate_withBanwords() {

        String testSentence = "This is and 게임머니 something  in this context.";
        BanwordValidationResultDTO result = banwordService.validate(testSentence);


        Collection<PayloadEmit<BanwordDTO>> foundWords = result.getBanwordsFound();
        assertEquals(1, foundWords.size(), "Should find 1 banwords.");
        foundWords.forEach(emit -> {
            assertTrue(
                    "게임머니".equals(emit.getPayload().getWord()),
                "The found word should be '게임머니'.");
        });
        log.info("금지어 포함 문장 : " + testSentence);
        log.info("결과 : " + foundWords);
    }

    @Test
    public void testValidate_noBanwords() {
        String testSentence = "This is a clean sentence with no bad content.";
        BanwordValidationResultDTO result = banwordService.validate(testSentence);

        log.info("금지어 미포함 문장 : " + testSentence);
        log.info("결과 : 통과");

        assertTrue(result.getBanwordsFound().isEmpty(), "No banword should be found.");
    }
}
