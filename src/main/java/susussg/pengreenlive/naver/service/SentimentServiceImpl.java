package susussg.pengreenlive.naver.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.naver.dto.SentimentRequestDTO;
import susussg.pengreenlive.naver.mapper.ReviewSummaryMapper;

@Log4j2
@Service
public class SentimentServiceImpl implements SentimentService {

  private final ReviewSummaryMapper reviewSummaryMapper;

  @Value("${naver.api.key.id}")
  private String apiKeyId;

  @Value("${naver.api.key.secret}")
  private String apiKeySecret;

  public SentimentServiceImpl(ReviewSummaryMapper reviewSummaryMapper) {
    this.reviewSummaryMapper = reviewSummaryMapper;
  }

  @Override
  public String ReviewsByProductSeq(Long productSeq) {
    List<String> content = reviewSummaryMapper.getReviewContentsByProductSeq(productSeq);
    return String.join("", content);
  }

  @Override
  public String SentimentReviews(String content) {

    try {
      URL url = new URL("https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", apiKeyId);
      connection.setRequestProperty("X-NCP-APIGW-API-KEY", apiKeySecret);
      connection.setDoOutput(true);

      SentimentRequestDTO requestDTO = new SentimentRequestDTO(content);
      Gson gson = new Gson();
      String requestBody = gson.toJson(requestDTO);
      log.info("Request Body: " + requestBody);

      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = requestBody.getBytes("utf-8");
        os.write(input, 0, input.length);
      }

      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
          StringBuilder response = new StringBuilder();
          String responseLine;
          while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
          }

          Type type = new TypeToken<Map<String, Object>>() {}.getType();
          return gson.fromJson(response.toString(), type).toString();
        }
      } else {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
          StringBuilder errorResponse = new StringBuilder();
          String responseLine;
          while ((responseLine = br.readLine()) != null) {
            errorResponse.append(responseLine.trim());
          }
          log.error("Error Response: " + errorResponse.toString());
          throw new RuntimeException("Failed : HTTP error code : " + responseCode + " : " + errorResponse.toString());
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Exception occurred while analyzing sentiment", e);
    }
  }
}
