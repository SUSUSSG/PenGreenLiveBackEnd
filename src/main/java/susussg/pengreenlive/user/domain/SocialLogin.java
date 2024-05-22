package susussg.pengreenlive.user.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import susussg.pengreenlive.order.util.UUIDConverter;

@Data
@Entity
public class SocialLogin {
    @Id
    @Column(name="SOCIAL_CD")
    private String socialCd;

    @Id
    @Convert(converter = UUIDConverter.class)
    @Column(name = "USER_UUID", columnDefinition = "BINARY(16)")
    private String userUuid;

    @Column(name="EXTERNAL_ID")
    private String externalId;

    @Column(name="ACCESS_TOKEN")
    private String accessToken;

}
