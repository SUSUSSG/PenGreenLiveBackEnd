package susussg.pengreenlive.chat.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import susussg.pengreenlive.chat.model.ChatRoom;
import susussg.pengreenlive.chat.repo.ChatRoomRepository;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    @Operation(summary = "방 정보를 조회합니다.", description = "채팅 방의 정보를 받아옵니다.")
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }
    @Operation(summary = "방 리스트를 받아옵니다.", description = "모든 방 리스트를 조회합니다.")
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    @Operation(summary = "방을 생성합니다.", description = "방의 이름을 받아서 방을 생성합니다.")
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }
    @Operation(summary = "방 입장을 처리합니다.", description = "선택한 방의 세부 정보를 받아와 입장 처리를 진행합니다.")
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }
    @Operation(summary = "방 세부 정보 조회", description = "방의 세부 정보를 받아옵니다.")
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}
