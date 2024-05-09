package susussg.pengreenlive.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import susussg.pengreenlive.chat.entity.User;

public interface WebsocketUserRepository extends JpaRepository<User, Long> {
}
