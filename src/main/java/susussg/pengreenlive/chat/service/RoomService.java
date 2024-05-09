package susussg.pengreenlive.chat.service;


import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.chat.Result;
import susussg.pengreenlive.chat.dto.EnteredRoomResponseDto;
import susussg.pengreenlive.chat.dto.RoomResponseDto;
import susussg.pengreenlive.chat.entity.EnteredRoom;
import susussg.pengreenlive.chat.entity.Room;
import susussg.pengreenlive.chat.entity.RoomStatus;
import susussg.pengreenlive.chat.entity.User;
import susussg.pengreenlive.chat.repository.WebsocketEnteredRoomRepository;
import susussg.pengreenlive.chat.repository.WebsocketRoomRepository;
import susussg.pengreenlive.chat.repository.WebsocketUserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final WebsocketRoomRepository repository;
    private final WebsocketEnteredRoomRepository enteredRoomRepository;
    private final WebsocketUserRepository userRepository;

    @Transactional
    public Long save(Room room) {
        return repository.save(room).getId();
    }


    public Result<List<RoomResponseDto>> findAll() {
        List<Room> rooms = repository.findAll();
        List<RoomResponseDto> collect = rooms.stream().map(RoomResponseDto::new).collect(Collectors.toList());

        return new Result<>(collect);
    }

    public Result<List<EnteredRoomResponseDto>> findAll(Long userId) {
        User user = userRepository.findById(userId).get();
        List<EnteredRoom> findRooms = enteredRoomRepository.findAll(RoomStatus.ENTER, user);

        List<EnteredRoomResponseDto> collect = findRooms.stream().map(EnteredRoomResponseDto::new).collect(Collectors.toList());

        return new Result<>(collect);

    }
}
