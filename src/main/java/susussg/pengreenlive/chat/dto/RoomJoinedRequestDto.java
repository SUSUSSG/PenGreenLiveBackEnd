package susussg.pengreenlive.chat.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomJoinedRequestDto {

    private Long userId;

    public RoomJoinedRequestDto(Long userId) {
        this.userId  = userId;
    }
}
