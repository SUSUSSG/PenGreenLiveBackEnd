package susussg.pengreenlive.user.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;
import susussg.pengreenlive.order.util.UUIDConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@DynamicInsert
@Table(name = "TB_USER")
public class TbUser {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Convert(converter = UUIDConverter.class)
    @Column(name = "USER_UUID", columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private String userUuid;

    @Column(name="USER_NM")
    private String userNm;

    @Column(name="USER_GENDER")
    private String userGender;

    @Column(name="USER_BIRTH_DT")
    private LocalDate userBirthDt;

    @Column(name = "USER_TEL")
    private String userTel;

    @Column(name="USER_EMAIL")
    private String userEmail;

    @Column(name="USER_ADDRESS")
    private String userAddress;

    @Column(name = "OPTIONAL_AGREEMENT_YN")
    private Boolean optionalAgreementYn;

    @ColumnDefault("true")
    @Column(name = "ACCOUNT_ACTIVE")
    private Boolean accountActive;

    @Column(name = "CREATE_ACCOUNT_DT")
    private LocalDateTime createAccountDt;

    @Column(name = "USER_PROFILE_IMG")
    private String userProfileImg;
}