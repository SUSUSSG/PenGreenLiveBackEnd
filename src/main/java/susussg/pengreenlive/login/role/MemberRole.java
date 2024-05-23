package susussg.pengreenlive.login.role;

import lombok.Getter;

@Getter
public enum MemberRole {
    VENDOR("VENDOR"),
    USER("USER"),
    DEACTIVE("DEACTIVE");


    MemberRole(String value) {
        this.value = value;
    }

    private String value;
}
