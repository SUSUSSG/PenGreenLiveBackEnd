package susussg.pengreenlive.util.Service;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.util.DTO.BanwordValidationResultDTO;
import susussg.pengreenlive.util.Service.BanwordService;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@SpringBootTest
public class BanwordServiceTest {

    @Autowired
    private BanwordService banwordService;

    @Test
    public void testBanwordFiltering() {
        // 테스트 문장
        String testSentence = "안녕하세요, 저는 롯데 팬입니다. 호스트바.";
        // 금칙어 필터링 실행
        BanwordValidationResultDTO result = banwordService.validate(testSentence);
        // 필터링된 금칙어 개수 검증 (2건이 필터링되어야 함)
        log.info(result);
        assertThat(result.getBanwordsFound().size()).isEqualTo(2);
    }
}
