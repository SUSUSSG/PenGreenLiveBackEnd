package susussg.pengreenlive.login.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@ToString
@JsonAutoDetect
public class Member extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userUuid;    // uuid
    private String userNm;      // 이름

    public Member(String username, String password, Collection<? extends GrantedAuthority> authorities, String userNm, String userUuid) {
        super(username, password, authorities);
        this.userUuid = userUuid;
        this.userNm = userNm;
    }
}
