package susussg.pengreenlive.openapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GreenProductService {

    private static final Logger logger = LoggerFactory.getLogger(GreenProductService.class);

    @Value("${api.certKeyc}")
    private String certKeyc;

    public String getGreenProductInfo(String prodlxid) {
        String url = "https://api.ecosq.or.kr/open-api/open-api/rest/GreenProductInformationInquiryService2.do";
        String completeUrl = url + "?certKeyc=" + certKeyc + "&prodlxid=" + prodlxid + "&pageRows=1";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(completeUrl, String.class);

        logger.info("Received response: {}", response);
        return response;
    }
}
