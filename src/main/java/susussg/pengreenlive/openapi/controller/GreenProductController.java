package susussg.pengreenlive.openapi.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.openapi.service.GreenProductService;

@Log4j2
@RestController
public class GreenProductController {
    @Autowired
    private GreenProductService greenProductService;

    @GetMapping("/green-product")
    public String getGreenProductInfo(@RequestParam String prodlxid) {
        return greenProductService.getGreenProductInfo(prodlxid);
    }
}
