package susussg.pengreenlive.chat.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.chat.entity.EnteredRoom;
import susussg.pengreenlive.chat.entity.Room;
import susussg.pengreenlive.chat.entity.User;
import susussg.pengreenlive.chat.repository.WebsocketEnteredRoomRepository;
import susussg.pengreenlive.chat.repository.WebsocketRoomRepository;
import susussg.pengreenlive.chat.repository.WebsocketUserRepository;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnteredRoomService {

    private final WebsocketEnteredRoomRepository repository;
    private final WebsocketUserRepository userRepository;
    private final WebsocketRoomRepository roomRepository;

    @Transactional
    public Long save(Long userId, Long roomId) {

        User user = userRepository.findById(userId).get();
        Room room = roomRepository.findById(roomId).get();
        EnteredRoom enteredRoom = new EnteredRoom(user, room);

        return repository.save(enteredRoom).getId();
    }
}
