package susussg.pengreenlive.main.Controller;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.main.DTO.MainCarouselDTO;
import susussg.pengreenlive.main.Service.MainService;

@Log4j2
@RestController
public class MainController {

    @Autowired
    MainService mainService;

    @GetMapping("/main-carousels")
    public List<MainCarouselDTO> getMainCarousels(){
        log.info("call getMainCarousels");
        return mainService.getMainCarousels();
    }
}
