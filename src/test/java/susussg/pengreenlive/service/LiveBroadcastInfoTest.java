package susussg.pengreenlive.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastInfoDTO;
import susussg.pengreenlive.broadcast.service.LiveBroadcastInfoService;

@SpringBootTest
@Log4j2
public class LiveBroadcastInfoTest {

    @Autowired
    private LiveBroadcastInfoService liveBroadcastInfoService;


    @Test
    public void getBasicBroadcastInfo() {
        long broadcastId = 14;
        LiveBroadcastInfoDTO info = liveBroadcastInfoService.getBasicBroadcastInfo(broadcastId);
        log.info(info.toString());
    }
}
