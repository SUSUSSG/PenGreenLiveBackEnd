package susussg.pengreenlive.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.broadcast.dto.BroadcastTimeDTO;
import susussg.pengreenlive.broadcast.mapper.BroadcastMapper;

import java.time.LocalDateTime;

@SpringBootTest
@Log4j2
public class BroadcastMapperTest {

    @Autowired
    private BroadcastMapper broadcastMapper;

    @Test
    public void updateTime() {
        BroadcastTimeDTO broadcastTime = BroadcastTimeDTO.builder()
                .broadcastSeq(11)
                .time(LocalDateTime.parse("2024-05-22T10:29:00"))
                .action("end")
                .build();
        broadcastMapper.updateBroadcastEndTime(broadcastTime);
    }
}
