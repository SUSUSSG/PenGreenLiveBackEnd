package susussg.pengreenlive.chat.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import susussg.pengreenlive.chat.entity.Room;

@Getter
@NoArgsConstructor
public class RoomResponseDto {

    private String name;
    private Long roomId;

    public RoomResponseDto(Room room) {
        this.name = room.getName();
        this.roomId = room.getId();
    }
}
