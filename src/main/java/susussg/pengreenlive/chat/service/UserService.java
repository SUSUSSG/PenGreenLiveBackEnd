package susussg.pengreenlive.chat.service;


import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import susussg.pengreenlive.chat.entity.User;
import susussg.pengreenlive.chat.repository.WebsocketUserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final WebsocketUserRepository repository;

    @Transactional
    public Long save(User user) {
        return repository.save(user).getId();
    }
}
