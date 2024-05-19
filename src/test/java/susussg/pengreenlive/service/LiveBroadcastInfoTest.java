package susussg.pengreenlive.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastInfoDTO;
import susussg.pengreenlive.broadcast.service.LiveBroadcastService;

@SpringBootTest
@Log4j2
public class LiveBroadcastInfoTest {

    @Autowired
    private LiveBroadcastService liveBroadcastService;


    @Test
    public void getBasicBroadcastInfo() {
        long broadcastId = 14;
        LiveBroadcastInfoDTO info = liveBroadcastService.getBroadcastInfo(broadcastId);
        log.info(info.toString());
    }
}
