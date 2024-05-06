//package susussg.pengreenlive.chat.rest;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//import susussg.pengreenlive.chat.vo.SocketVO;
//
//@Controller
//public class SocketController {
//
//    // /receive를 메시지를 받을 endpoint로 설정합니다.
//    @MessageMapping("/receive")
//
//    // /send로 메시지를 반환합니다.
//    @SendTo("/send")
//
//    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
//    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.
//    public SocketVO SocketHandler(SocketVO socketVO) {
//        // vo에서 getter로 userName을 가져옵니다.
//        String userName = socketVO.getUserName();
//        // vo에서 setter로 content를 가져옵니다.
//        String content = socketVO.getContent();
//
//        // 생성자로 반환값을 생성합니다.
//        SocketVO result = new SocketVO(userName, content);
//        // 반환
//        return result;
//    }
//}
package susussg.pengreenlive.chat.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import susussg.pengreenlive.chat.vo.SocketVO;

@Controller
public class SocketController {

    private static final Logger logger = LoggerFactory.getLogger(SocketController.class);

    @MessageMapping("/receive")
    @SendTo("/send")
    public SocketVO SocketHandler(SocketVO socketVO) {
        String userName = socketVO.getUserName();
        String content = socketVO.getContent();

        // Log the incoming message

        System.out.printf("Received message from '%s': %s", userName, content);
        SocketVO result = new SocketVO(userName, content);

        // Log the outgoing message
        System.out.printf("sending message from '%s': %s", userName, content);

        return result;
    }
}