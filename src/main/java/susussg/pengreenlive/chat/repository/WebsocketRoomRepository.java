package susussg.pengreenlive.chat.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import susussg.pengreenlive.chat.entity.Room;
import susussg.pengreenlive.chat.entity.RoomStatus;

public interface WebsocketRoomRepository extends JpaRepository<Room, Long> {

    @Query("select distinct r from Room r join fetch EnteredRoom e where e.roomStatus != :roomStatus")
    List<Room> findAll(@Param(value = "roomStatus") RoomStatus roomStatus);
}
