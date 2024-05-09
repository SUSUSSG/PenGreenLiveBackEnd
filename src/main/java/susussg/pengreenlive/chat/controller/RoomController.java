package susussg.pengreenlive.chat.controller;


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

    @GetMapping("")
    public Result<List<RoomResponseDto>> list() {

        return service.findAll();
    }

    @GetMapping("/joined")
    public Result<List<EnteredRoomResponseDto>> joinedList(RoomJoinedRequestDto requestDto) {
        return service.findAll(requestDto.getUserId());
    }
}
