package susussg.pengreenlive.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import susussg.pengreenlive.chat.entity.EnteredRoom;

@NoArgsConstructor
@Getter
public class EnteredRoomResponseDto {

    private Long roomId;
    private String roomName;

    public EnteredRoomResponseDto(EnteredRoom rs) {
        this.roomName = rs.getRoom().getName();
        this.roomId = rs.getRoom().getId();
    }
}
