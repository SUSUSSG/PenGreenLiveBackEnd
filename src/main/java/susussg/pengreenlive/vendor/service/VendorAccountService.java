package susussg.pengreenlive.vendor.service;

import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.HttpClientErrorException;
import susussg.pengreenlive.login.dto.Member;
import susussg.pengreenlive.user.Mapper.UserMapper;
import susussg.pengreenlive.user.domain.SocialLogin;
import susussg.pengreenlive.user.domain.TbUser;
import susussg.pengreenlive.user.dto.SignupFormDTO;
import susussg.pengreenlive.vendor.dto.VendorSignupFormDTO;
import susussg.pengreenlive.vendor.mapper.VendorMapper;

@Slf4j
@RequiredArgsConstructor
@Service
public class VendorAccountService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    VendorMapper vendorMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${saupja.api.key}")
    private String apiKey;

    public void createVendor(VendorSignupFormDTO signupForm) {
        log.info("service {}", signupForm);

        try {
            signupForm.setVendorPw(passwordEncoder.encode(signupForm.getVendorPw()));
            vendorMapper.insertVendor(signupForm);
            Long vendorSeq = signupForm.getVendorSeq();
            vendorMapper.updateChannelSeq(vendorSeq);
            vendorMapper.insertChannel(vendorSeq);
        } catch (Exception e) {
            log.info("error {}", e.getMessage());
        }
    }

    public Member selectByBusinessId(String businessId) {
        return null;
    }

    public Map<String, Object> validateBusinessNumber(String businessNumber) {
        String apiUrl = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" + apiKey;
        log.info("API URL: {}", apiUrl); // API URL을 로그로 출력하여 확인
        log.info("Business Number: {}", businessNumber); // 사업자 번호 로그 출력

        Map<String, Object> request = new HashMap<>();
        request.put("b_no", new String[]{businessNumber});

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest;
        try {
            jsonRequest = objectMapper.writeValueAsString(request);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize request to JSON", e);
        }

        log.info("Request JSON: {}", jsonRequest); // 요청 JSON 로그 출력

        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(jsonRequest, StandardCharsets.UTF_8));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpPost)) {

            String jsonResponse = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            log.info("API Response: {}", jsonResponse); // 응답 로그 출력

            Map<String, Object> responseBody = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {});
            if (responseBody != null && responseBody.containsKey("data")) {
                List<Map<String, Object>> dataList = (List<Map<String, Object>>) responseBody.get("data");
                if (!dataList.isEmpty()) {
                    return dataList.get(0);
                } else {
                    throw new RuntimeException("Business number validation failed: empty data list");
                }
            } else {
                throw new RuntimeException("Business number validation failed: no data field");
            }
        } catch (HttpClientErrorException e) {
            log.error("HTTP Error: {}", e.getStatusCode());
            log.error("Response Body: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Business number validation failed", e);
        } catch (Exception e) {
            log.error("Error during business number validation", e);
            throw new RuntimeException("Business number validation failed", e);
        }
    }
}
