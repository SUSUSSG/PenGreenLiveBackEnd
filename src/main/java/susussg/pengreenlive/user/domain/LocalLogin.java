package susussg.pengreenlive.user.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import susussg.pengreenlive.order.util.UUIDConverter;

@Data
@Entity
public class LocalLogin {
    @Id
    @Convert(converter = UUIDConverter.class)
    @Column(name = "USER_UUID", columnDefinition = "BINARY(16)")
    private String userUuid;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PW")
    private String userPw;
}
