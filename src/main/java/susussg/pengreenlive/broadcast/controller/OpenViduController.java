package susussg.pengreenlive.broadcast.controller;

import java.util.Map;

import javax.annotation.PostConstruct;

import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class OpenViduController {

    @Value("${OPENVIDU_URL}")
    private String OPENVIDU_URL;

    @Value("${OPENVIDU_SECRET}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    /**
     * @param params The Session properties
     * @return The Session ID
     */
    @Operation(summary = "세션을 초기화합니다.", description = "세션 속성을 받아 새로운 세션을 초기화합니다.")
    @PostMapping("/api/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    /**
     * @param sessionId The Session in which to create the Connection
     * @param params    The Connection properties
     * @return The Token associated to the Connection
     */
    @Operation(summary = "연결을 생성합니다.", description = "세션 ID와 연결 속성을 받아 새로운 연결을 생성합니다.")
    @PostMapping("/api/sessions/{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(sessionId);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

    /**
     * 현재 시청자 수를 가져옵니다.
     * @param sessionId
     * @return
     */
    @Operation(summary = "현재 시청자 수를 조회합니다.", description = "세션 ID를 통해 현재 시청자 수를 조회합니다.")
    @GetMapping("/api/sessions/{sessionId}/connections/count")
    public ResponseEntity<Integer> getConnectionCount(@PathVariable("sessionId") String sessionId) {
        try {
            Session session = openvidu.getActiveSession(sessionId);
            if (session != null) {
                session.fetch();
                int connectionCount = session.getActiveConnections().size();
                return new ResponseEntity<>(connectionCount, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
