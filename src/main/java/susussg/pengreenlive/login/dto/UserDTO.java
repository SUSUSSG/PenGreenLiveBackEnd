package susussg.pengreenlive.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userUuid;
    private String userNm;
    private String userGender;
    private LocalDate userBirthDt;
    private String userTel;
    private String userEmail;
    private String userAddress;
    private Boolean optionalAgreementYn;
    private Boolean accountActive;
    private String role;

    private String userId;
    private String userPw;
}
