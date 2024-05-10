package susussg.pengreenlive.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.broadcast.dto.BroadcastDTO;
import susussg.pengreenlive.broadcast.dto.BroadcastProductDTO;
import susussg.pengreenlive.broadcast.mapper.LiveRegisterMapper;

import java.sql.Blob;
import java.util.Date;

@SpringBootTest
@Log4j2
public class LiveRegisterMapperTest {

    @Autowired
    private LiveRegisterMapper liveRegisterMapper;

    @Test
    void insertBroadcast() {
        BroadcastDTO broad = BroadcastDTO.builder()
                .broadcastSeq(4)
                .channelNm("test")
                .broadcastTitle("test")
//                .broadcastImage("imageData")
                .broadcastSummary("test")
                .broadcastScheduledTime(new Date(2024, 10, 11))
                .categoryCd("test")
                .build();
        int result = liveRegisterMapper.insertBroadcast(broad);
        if (result == 1) {
            log.info("성공");
        } else {
            log.error("실패");
        }
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
}
