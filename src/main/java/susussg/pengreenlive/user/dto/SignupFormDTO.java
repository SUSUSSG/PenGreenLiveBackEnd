package susussg.pengreenlive.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupFormDTO {
    private String userUuid;
    private String userNm;
    private String userGender;
    private LocalDate userBirthDt;
    private String userTel;
    private String userEmail;
    private String userAddress;
    private Boolean optionalAgreementYn;

    private String userId;
    private String userPw;
}
