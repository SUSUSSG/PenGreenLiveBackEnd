package susussg.pengreenlive.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import susussg.pengreenlive.login.service.SecurityService;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/brandpay")
public class BrandPayController {

    @Value("${toss.secretKey}")
    private String secretKey;

    @Value("${toss.clientKey}")
    private String clientKey;

    @Autowired
    private SecurityService securityService;

    @Operation(summary = "액세스 토큰 발급", description = "토스 브랜드페이 API로 부터 액세스 토큰을 발급받습니다.")
    @PostMapping("/access-token")
    public ResponseEntity<?> getAccessToken(@RequestBody Map<String, String> payload) {
        String customerKey = securityService.getCurrentUserUuid();

        log.info("access");
        String code = payload.get("code");
        String url = "https://api.tosspayments.com/v1/brandpay/authorizations/access-token";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(secretKey, "");
        headers.set("Content-Type", "application/json");

        Map<String, String> body = new HashMap<>();
        body.put("grantType", "AuthorizationCode");
        body.put("customerKey", customerKey);
        body.put("code", code);

        ResponseEntity<Map> response;
        try {
            response = restTemplate.postForEntity(url, new HttpEntity<>(body, headers), Map.class);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        log.info("access token {}", response);
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    @Operation(summary = "액세스 토큰 발급", description = "토스 브랜드페이로 부터 콜백을 요청해서 액세스 토큰을 발급받습니다.")
    @GetMapping("/callback-auth")
    public ResponseEntity<String> callbackAuth(@RequestParam("code") String code,
                                               @RequestParam("customerKey") String customerKey) {
        String url = "https://api.tosspayments.com/v1/brandpay/authorizations/access-token";

        HttpHeaders headers = new HttpHeaders();
        String auth = secretKey + ":";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        headers.set("Content-Type", "application/json");

        Map<String, String> body = new HashMap<>();
        body.put("grantType", "AuthorizationCode");
        body.put("code", code);
        body.put("customerKey", customerKey);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            log.error("HTTP Client Error: Status code {}, Response body {}", e.getStatusCode(), e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Exception: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body("Failed to retrieve access token");
        }
    }

    @Operation(summary = "브랜드페이 결제 승인", description = "토스 브랜드페이 결제 승인 요청")
    @PostMapping("/confirm")
    public ResponseEntity<JSONObject> confirmBrandpayPayment(@RequestBody String jsonBody) throws Exception {
        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        String customerKey = securityService.getCurrentUserUuid();

        try {
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        };
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);
        obj.put("customerKey", customerKey);

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        URL url = new URL("https://api.tosspayments.com/v1/brandpay/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        return ResponseEntity.status(code).body(jsonObject);

    }
}
