package susussg.pengreenlive.chat.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CHAT_USER")
public class User {

    @Id @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "user")
    private List<EnteredRoom> enteredRoom = new ArrayList<>();

    public User (String name) {
        this.name = name;
    }

}
