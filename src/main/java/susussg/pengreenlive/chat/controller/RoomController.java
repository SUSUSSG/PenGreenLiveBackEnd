package susussg.pengreenlive.chat.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import susussg.pengreenlive.chat.Result;
import susussg.pengreenlive.chat.dto.EnteredRoomResponseDto;
import susussg.pengreenlive.chat.dto.RoomJoinedRequestDto;
import susussg.pengreenlive.chat.dto.RoomResponseDto;
import susussg.pengreenlive.chat.service.RoomService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room")
public class RoomController {

    private final RoomService service;
    @Operation(summary = "모든 방의 리스트 조회", description = "모든 방 리스트를 가져옵니다.")
    @GetMapping("")
    public Result<List<RoomResponseDto>> list() {

        return service.findAll();
    }
    @Operation(summary = "참여방 조회", description = "참여하고 있는 방의 정보를 받아옵니다.")
    @GetMapping("/joined")
    public Result<List<EnteredRoomResponseDto>> joinedList(RoomJoinedRequestDto requestDto) {
        return service.findAll(requestDto.getUserId());
    }
}
