package susussg.pengreenlive.naver.service;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.naver.dto.DocumentDTO;
import susussg.pengreenlive.naver.dto.OptionDTO;
import susussg.pengreenlive.naver.dto.SummaryRequestDTO;
import susussg.pengreenlive.naver.mapper.ReviewSummaryMapper;

@Log4j2
@Service
public class ReviewSummaryServiceImpl implements ReviewSummaryService {

  private final ReviewSummaryMapper reviewSummaryMapper;

  @Value("${naver.api.key.id}")
  private String apiKeyId;

  @Value("${naver.api.key.secret}")
  private String apiKeySecret;

  @Autowired
  public ReviewSummaryServiceImpl(ReviewSummaryMapper reviewSummaryMapper) {
    this.reviewSummaryMapper = reviewSummaryMapper;
  }

  @Override
  public String ReviewsByProductSeq(Long productSeq) {
    List<String> content = reviewSummaryMapper.getReviewContentsByProductSeq(productSeq);
    String reviewcontent = String.join("", content);
    return reviewcontent;
  }

  public String SummarizeReviews(String review) {
    DocumentDTO document = new DocumentDTO(review);
    OptionDTO option = new OptionDTO("ko", "general", 0, 2);

    SummaryRequestDTO summaryRequest = new SummaryRequestDTO(document, option);
    String requestBody = new Gson().toJson(summaryRequest);

    log.info("Request Body: " + requestBody);

    try {
      URL url = new URL("https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
      connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", apiKeyId);
      connection.setRequestProperty("X-NCP-APIGW-API-KEY", apiKeySecret);
      connection.setDoOutput(true);

      // Send request body
      try (OutputStream os = connection.getOutputStream()) {
        byte[] input = requestBody.getBytes("utf-8");
        os.write(input, 0, input.length);
      }

      int responseCode = connection.getResponseCode();
      log.info("Response Code: " + responseCode);
      if (responseCode != HttpURLConnection.HTTP_OK) {
        String errorResponse = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "utf-8"))) {
          StringBuilder response = new StringBuilder();
          String responseLine;
          while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
          }
          errorResponse = response.toString();
        }
        log.error("Error Response: " + errorResponse);

        if(errorResponse.contains("E100")){
          log.info("리뷰 분석을 위한 리뷰수가 부족합니다.");
          return "리뷰 분석을 위한 리뷰수가 부족합니다.";
        }

        throw new RuntimeException("Bad request: " + connection.getResponseMessage() + ", Error: " + errorResponse);
      }

      // Read response
      try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
          response.append(responseLine.trim());
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        String summary = jsonResponse.getString("summary");
        log.info("Summary: " + summary);
        return summary;

      } catch (IOException e) {
        log.error("Failed to read response from Naver API", e);
        throw new RuntimeException("Failed to read response from Naver API", e);
      }

    } catch (IOException e) {
      log.error("Failed to send request to Naver API", e);
      throw new RuntimeException("Failed to send request to Naver API", e);
    }
  }
}
