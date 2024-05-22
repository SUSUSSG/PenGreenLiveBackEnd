package susussg.pengreenlive.user.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import susussg.pengreenlive.order.dto.OrderFormDTO;
import susussg.pengreenlive.order.service.OrderService;
import susussg.pengreenlive.user.dto.SignupFormDTO;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Log4j2
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    @Test
    public void testSignup() {
        SignupFormDTO signupForm = SignupFormDTO.builder()
                .userNm("장서윤")
                .userGender("여성")
                .userId("seoy316")
                .userPw("123456")
                .userBirthDt(LocalDate.parse("1998-03-16"))
                .userTel("01096543115")
                .userEmail("seoy316@naver.com")
                .userAddress("도봉로 315, 1908호")
                .optionalAgreementYn(true)
                .build();

        accountService.createLocalUser(signupForm);
    }

    @Test
    public void uuidTest() {
        // 테스트할 UUID 문자열 생성
        String uuidString = UUID.randomUUID().toString();

        // UUID 문자열을 이진 데이터로 변환하고 다시 문자열로 복원하는 테스트
        testUUIDConversion(uuidString);
    }

    // UUID 변환 테스트
    private static void testUUIDConversion(String uuidString) {
        byte[] binaryUUID = uuidToBin(uuidString);
        String restoredUUIDString = binToUuid(binaryUUID);

        System.out.println("Original UUID: " + uuidString);
        System.out.println("binaryUUID = " + binaryUUID);
        System.out.println("Restored UUID from binary: " + restoredUUIDString);

        if (uuidString.equals(restoredUUIDString)) {
            System.out.println("Conversion is successful.");
        } else {
            System.out.println("Conversion failed.");
        }
    }

    // UUID 문자열을 이진 데이터로 변환
    private static byte[] uuidToBin(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    // 이진 데이터를 UUID 문자열로 변환
    private static String binToUuid(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        UUID uuid = new UUID(high, low);
        return uuid.toString();
    }
}
