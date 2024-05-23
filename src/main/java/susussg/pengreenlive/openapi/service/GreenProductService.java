package susussg.pengreenlive.openapi.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
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

    private static final Map<String, String> STATUS_CODE_MAP = new HashMap<>() {{
        put("01", "인증");
        put("02", "취소(재약정중)");
        put("03", "인증포기");
        put("04", "인증만료");
        put("05", "사용안함");
    }};

    private static final Map<String, String> RESPONSE_CODE_MAP = new HashMap<>() {{
        put("", "null");
        put("09", "1");
        put("15", "2");
        put("63", "3");
        put("0915", "4");
        put("091563", "5");
        put("0963", "6");
        put("1563", "7");
    }};

    @Value("${api.certKeyc}")
    private String certKeyc;

    public String getGreenProductInfo(String prodIxid) {
        String url = "https://api.ecosq.or.kr/open-api/open-api/rest/GreenProductInformationInquiryService2.do";
        String completeUrl = url + "?certKeyc=" + certKeyc + "&prodIxid=" + prodIxid + "&pageRows=1";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(completeUrl, String.class);

        logger.info("Received product info response: {}", response);
        return parseProductInfo(response);
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

    private String parseProductInfo(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray dataArray = jsonResponse.optJSONArray("data");
        JSONObject result = new JSONObject();

        if (dataArray != null && dataArray.length() > 0) {
            JSONObject data = dataArray.getJSONObject(0);

            String prodPrnm = data.optString("prodPrnm");
            String prodInrs = data.optString("prodInrs");
            String prodRsst = data.optString("prodRsst");
            String prodRedt = data.optString("prodRedt");
            String prodCfgb = data.optString("prodCfgb");

            result.put("prodPrnm", prodPrnm);
            result.put("prodInrs", prodInrs);
            result.put("prodRsst", prodRsst);
            result.put("prodRedt", prodRedt);
            result.put("prodCfgb", RESPONSE_CODE_MAP.getOrDefault(prodCfgb, "Unknown"));

            if (!"01".equals(prodRsst)) {
                String statusDescription = STATUS_CODE_MAP.getOrDefault(prodRsst, "Unknown Status");
                logger.warn("Product certification status is not valid: {}", statusDescription);
            }
        } else {
            logger.error("No data found in the response.");
        }

        return result.toString();
    }
}