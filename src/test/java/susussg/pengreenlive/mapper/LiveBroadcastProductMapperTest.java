package susussg.pengreenlive.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import susussg.pengreenlive.broadcast.dto.LiveBroadcastProductDTO;
import susussg.pengreenlive.broadcast.mapper.LiveBroadcastMapper;

import java.util.List;

@SpringBootTest
@Log4j2
public class LiveBroadcastProductMapperTest {

    @Autowired
    private LiveBroadcastMapper liveBroadcastMapper;

    @Test
    public void selectBroadcastProduct() {
        List<LiveBroadcastProductDTO> productList = liveBroadcastMapper.selectBroadcastProduct(11);
        log.info(productList.toString());

    }

}
