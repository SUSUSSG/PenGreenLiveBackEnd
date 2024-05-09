package susussg.pengreenlive.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import susussg.pengreenlive.chat.entity.EnteredRoom;

@NoArgsConstructor
@Getter
public class EnteredRoomDto {

    private String username;

    public EnteredRoomDto(EnteredRoom r) {
        this.username = r.getUser().getName();
    }
}
