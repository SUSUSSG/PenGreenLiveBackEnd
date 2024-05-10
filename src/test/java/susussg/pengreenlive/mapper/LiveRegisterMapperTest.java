package susussg.pengreenlive.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.broadcast.dto.BroadcastDTO;
import susussg.pengreenlive.broadcast.dto.BroadcastProductDTO;
import susussg.pengreenlive.broadcast.dto.NoticeDTO;
import susussg.pengreenlive.broadcast.mapper.LiveRegisterMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.util.Date;

@SpringBootTest
@Log4j2
public class LiveRegisterMapperTest {

    @Autowired
    private LiveRegisterMapper liveRegisterMapper;

    @Test
    void insertBroadcast() throws IOException {

        File file = new File("/Users/jinii/Downloads/가전제품-노트북.png");
        byte[] imageData = Files.readAllBytes(file.toPath());

        BroadcastDTO broad = BroadcastDTO.builder()
                .channelNm("test")
                .broadcastTitle("test")
                .broadcastImage(imageData)
                .broadcastSummary("test")
                .broadcastScheduledTime(new Date(2024, 10, 11))
                .categoryCd("BCT-CTG-005")
                .build();
        liveRegisterMapper.insertBroadcast(broad);
        log.info("insert success!!!");
    }

    @Test
    void insertBroadcastProduct() {
        BroadcastProductDTO product = BroadcastProductDTO.builder()
                .broadcastSeq(1)
                .productSeq(1)
                .discountRate(1)
                .discountPrice(1)
                .build();

        int result = liveRegisterMapper.insertBroadcastProduct(product);
        if(result == 1) {
            log.info("성공");
        } else {
            log.error("실패");
        }
    }

    @Test
    void insertNotice() {
        NoticeDTO notice = NoticeDTO.builder()
                .broadcastSeq(1)
                .noticeContent("공지입니다~~")
                .build();

        int result = liveRegisterMapper.insertNotice(notice);
        if(result == 1) {
            log.info("성공");
        } else {
            log.error("실패");
        }
    }
}
