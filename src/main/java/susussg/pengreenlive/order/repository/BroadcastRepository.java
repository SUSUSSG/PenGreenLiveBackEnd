package susussg.pengreenlive.order.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import susussg.pengreenlive.order.domain.Broadcast;
import susussg.pengreenlive.order.dto.BroadcastStockDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BroadcastRepository extends JpaRepository<Broadcast, Long> {
    @Query("SELECT b.broadcastSeq FROM TB_BROADCAST b WHERE DATE(b.broadcastScheduledTime) = :today")
    List<Long> findAllByBroadcastScheduledTime(@Param("today") LocalDate today);
}