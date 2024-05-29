package susussg.pengreenlive.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserFormDTO {
    private String userUUID;
    private LocalDate userBirthDt;
    private String userTel;
    private String userEmail;
    private String userAddress;
    private String userId;
    private String userPw;
    private String userProfileImgFile;
    private String userProfileImg;
}
