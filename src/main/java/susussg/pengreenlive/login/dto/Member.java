package susussg.pengreenlive.login.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

@ToString
@Getter
public class Member extends User {

    private String userUuid;    // uuid
    private String userNm;      // 이름

    public Member(String username, String password, Collection<? extends GrantedAuthority> authorities, String userNm, String userUuid) {
        super(username, password, authorities);
        this.userUuid = userUuid;
        this.userNm = userNm;
    }

    public Member(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,  String userNm, String userUuid) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userUuid = userUuid;
        this.userNm = userNm;
    }

}
