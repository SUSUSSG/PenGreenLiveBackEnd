package susussg.pengreenlive.openai.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OpenAIService {

    private static final Logger logger = Logger.getLogger(OpenAIService.class.getName());

    @Value("${openai.api.key}")
    private String apiKey;

    private List<Map<String, Object>> messages = new ArrayList<>();

    public OpenAIService() {
        initializeMessages();
    }

    private void initializeMessages() {
        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "#Role"
            + "당신의 라이브커머스 웹사이트인 'PenGreenLive'의 챗봇이자 펭귄인 '슈슈슉'입니다."
            + "아래의 #Objective에 해당하는 내용이나 라이브커머스에 관한 질문이 아닌 경우 올바른 대화 주제로 이끌어주세요."
            + "#Objective"
            + "1. 유저가 구매한 상품이나 배송과 관련된 질문을 하는 경우 '@주문내역'이라고만 응답하세요."
            + "2. 유저가 프로필 설정이나 주소 설정과 같은 개인정보 관련 질문을 하는 경우 '@프로필'이라고만 응답하세요."
            + "3. 유저가 환불이나 교환 등의 문제가 있는 경우 '@환불'이라고만 응답하세요."
            + "4. 유저가 특정 상황이나 방송, 혹은 상품에 대해 질문하는 경우 그 상황과 어울리는 단어를 찾아 키워드로 포함시켜 '@방송,키워드'로만 응답하세요, 키워드는 질문과 가장 관련성이 높은 단어 하나입니다."
            + "5. 유저가 환경과 관련된 질문을 하는 경우 '@환경이야기'라고만 응답하세요."
            + "6. 유저가 최근 시청한 방송이나 무엇을 봤었는지 궁금해하는 질문인 경우 '@시청기록'이라고만 응답하세요."
            + "7. 유저가 결제 수단, 방법 등 질문을 하는 경우 '@결제수단'이라고만 응답하세요.");
        messages.add(systemMessage);
    }

    public String getChatbotResponse(String userMessage) throws Exception {
        Map<String, Object> userMessageMap = new HashMap<>();
        userMessageMap.put("role", "user");
        userMessageMap.put("content", userMessage);

        messages.add(userMessageMap);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o");
        body.put("messages", messages);
        body.put("max_tokens", 100);

        String requestBody = toJson(body);

        URL url = new URL("https://api.openai.com/v1/chat/completions");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        String responseBody = response.toString();
        String finalResponse = parseResponse(responseBody);

        // 응답을 반환한 후에 messages 초기화 및 시스템 메시지 다시 추가
        if (finalResponse.startsWith("@")) {
            initializeMessages();
        }

        logger.info("Response Body: " + responseBody);
        logger.info("finalResponse: " + finalResponse);

        return finalResponse;  // JSON 응답 본문을 반환합니다.
    }

    private String parseResponse(String responseBody) throws Exception {
        logger.info("Parsing Response Body: " + responseBody);  // 파싱할 응답 본문 로그 추가
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray choices = jsonResponse.getJSONArray("choices");
        if (choices.length() > 0) {
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            return message.getString("content").trim();
        } else {
            throw new Exception("Invalid response format");
        }
    }

    private String toJson(Map<String, Object> map) {
        return new JSONObject(map).toString();
    }

    private String valueToJson(Object value) {
        if (value instanceof String) {
            return "\"" + value + "\"";
        } else if (value instanceof Map) {
            return new JSONObject((Map<?, ?>) value).toString();
        } else if (value instanceof List) {
            List<?> list = (List<?>) value;
            return new JSONArray(list).toString();
        } else {
            return value.toString();
        }
    }

    // 새로운 메소드 추가
    public String checkReviewForHarmfulness(String reviewContent) throws Exception {
        logger.info("Starting review harmfulness check for content: " + reviewContent);

        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "#ROLE "
            + "당신은 리뷰의 유해성을 true/false로 판단하는 유해성 검사기입니다. "
            + "리뷰 내용을 제공하면, 유해성 여부에 따라 오직 true 혹은 false 로만 응답하세요. "
            + "리뷰 내용은 다음과 같습니다.");

        Map<String, Object> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", reviewContent);

        List<Map<String, Object>> reviewMessages = new ArrayList<>();
        reviewMessages.add(systemMessage);
        reviewMessages.add(userMessage);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o");
        body.put("messages", reviewMessages);
        body.put("max_tokens", 20);

        String requestBody = toJson(body);
        logger.info("Request Body: " + requestBody);

        URL url = new URL("https://api.openai.com/v1/chat/completions");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // 요청과 응답의 전체 내용을 로그로 출력
        logger.info("Sending request to URL: " + url.toString());
        logger.info("Request Method: " + conn.getRequestMethod());
        logger.info("Request Properties: " + conn.getRequestProperties().toString());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
            logger.info("Request Body Sent: " + requestBody);
        }

        StringBuilder response = new StringBuilder();
        int responseCode = conn.getResponseCode();
        logger.info("Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        } else {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                logger.severe("Error response: " + response.toString());
                throw new Exception("Error response from API: " + response.toString());
            }
        }

        String responseBody = response.toString();
        logger.info("Response Body: " + responseBody);

        // 응답에서 content 값을 추출
        JSONObject jsonResponse = new JSONObject(responseBody);
        JSONArray choices = jsonResponse.getJSONArray("choices");
        if (choices.length() > 0) {
            JSONObject message = choices.getJSONObject(0).getJSONObject("message");
            return message.getString("content").trim();
        } else {
            throw new Exception("Invalid response format");
        }
    }
}
