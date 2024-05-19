package susussg.pengreenlive.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastProductDTO;
import susussg.pengreenlive.broadcast.service.LiveBroadcastService;

import java.util.List;

@SpringBootTest
@Log4j2
public class LiveBroadcastProductTest {
    @Autowired
    private LiveBroadcastService liveBroadcastService;

    @Test
    public void getBroadcastProduct() {
        List<LiveBroadcastProductDTO> productList = liveBroadcastService.getBroadcastProducts(11);
        log.info(productList.toString());
    }
}
