package susussg.pengreenlive.openapi.service;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

        logger.info("Received product info response: {}", response);
        return response;
    }

    public String getProductImage(String prodIxid) {
        String url = "https://api.ecosq.or.kr/open-api/open-api/openProductImage.do?prodIxid=" + prodIxid;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            byte[] imageBytes = response.getBody();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            logger.info("Received product image response");
            return base64Image;
        } else {
            logger.error("Failed to receive product image response");
            return null;
        }
    }
}