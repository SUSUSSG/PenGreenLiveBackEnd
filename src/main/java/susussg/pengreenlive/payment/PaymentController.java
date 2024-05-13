package susussg.pengreenlive.payment;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import susussg.pengreenlive.payment.dto.verifyPaymentDTO;
import susussg.pengreenlive.payment.service.PaymentService;
import org.json.simple.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 금액 검증
    @PostMapping("/verify")
    public ResponseEntity<?> verifyPaymentRequest(@RequestBody verifyPaymentDTO paymentRequest, HttpSession session) {
        try {
            verifyPaymentDTO payment = (verifyPaymentDTO) session.getAttribute(paymentRequest.getOrderId());
            if (payment.equals(paymentRequest.getAmount())) {
                return ResponseEntity.ok().body("approved");
            }
            else {
                return ResponseEntity.ok().body("denied");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    /*
    * 결제 요청 전, 결제 정보 임시 저장
    * */
    @PostMapping("/hold-for-checkout")
    public ResponseEntity<?> holdPaymentInformation(@RequestBody verifyPaymentDTO paymentRequest, HttpSession session) {
        try {
            session.setAttribute(paymentRequest.getOrderId(), paymentRequest.getAmount());
            return ResponseEntity.ok().body("ok");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @RequestMapping(value = "/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
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

        log.info("PaymentController /confirm {}", obj);

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
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

        // 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        return ResponseEntity.status(code).body(jsonObject);
    }
}
