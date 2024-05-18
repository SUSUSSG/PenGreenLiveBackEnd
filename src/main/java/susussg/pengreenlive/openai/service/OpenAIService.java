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
        Map<String, Object> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "your name is susussg.");
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
        logger.info("Response Body: " + responseBody);

        return responseBody;  // JSON 응답 본문을 반환합니다.
    }

    public String parseResponse(String responseBody) throws Exception {
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
        return map.entrySet()
            .stream()
            .map(entry -> "\"" + entry.getKey() + "\": " + valueToJson(entry.getValue()))
            .collect(Collectors.joining(", ", "{", "}"));
    }

    private String valueToJson(Object value) {
        if (value instanceof String) {
            return "\"" + value + "\"";
        } else if (value instanceof Map) {
            return toJson((Map<String, Object>) value);
        } else if (value instanceof List) {
            List<?> list = (List<?>) value;
            return list.stream().map(this::valueToJson).collect(Collectors.joining(", ", "[", "]"));
        } else {
            return value.toString();
        }
    }
}
