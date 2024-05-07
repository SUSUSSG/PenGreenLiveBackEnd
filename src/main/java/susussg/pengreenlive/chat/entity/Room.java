package susussg.pengreenlive.chat.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "room")
    private List<EnteredRoom> enteredRoom = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }
}