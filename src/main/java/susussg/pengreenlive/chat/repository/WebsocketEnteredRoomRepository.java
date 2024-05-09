package susussg.pengreenlive.chat.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import susussg.pengreenlive.chat.entity.EnteredRoom;
import susussg.pengreenlive.chat.entity.RoomStatus;
import susussg.pengreenlive.chat.entity.User;

public interface WebsocketEnteredRoomRepository extends JpaRepository<EnteredRoom, Long> {

    @Query("SELECT er FROM EnteredRoom er JOIN FETCH er.room r WHERE er.user = :user AND er.roomStatus = :roomStatus")
    List<EnteredRoom> findAll(@Param("roomStatus") RoomStatus roomStatus, @Param("user") User user);
}
