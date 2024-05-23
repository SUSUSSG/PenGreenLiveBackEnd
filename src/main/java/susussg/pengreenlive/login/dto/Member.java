package susussg.pengreenlive.login.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String userUuid;
    private String userNm;
    private String role;
    private String userId;
    private String userPw;
}
