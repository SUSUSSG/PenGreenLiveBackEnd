package susussg.pengreenlive.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import susussg.pengreenlive.openapi.service.GreenProductService;

@Log4j2
@RestController
@RequestMapping("/api")
public class GreenProductController {

    @Autowired
    private GreenProductService greenProductService;

    @Operation(summary = "녹색인증 정보를 조회합니다.", description = "녹색인증코드를 받아 정보와 유효성을 확인합니다.")
    @GetMapping("/green-product")
    public String getGreenProductInfo(@RequestParam String prodIxid) {
        String productInfo = greenProductService.getGreenProductInfo(prodIxid);
        String productImage = greenProductService.getProductImage(prodIxid);

        JSONObject responseJson = new JSONObject();
        responseJson.put("productInfo", new JSONObject(productInfo));
        responseJson.put("productImage", productImage); // Base64 encoded image string

        return responseJson.toString();
    }
}