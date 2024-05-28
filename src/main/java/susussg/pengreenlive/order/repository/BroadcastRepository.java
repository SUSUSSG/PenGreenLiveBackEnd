package susussg.pengreenlive.order.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import susussg.pengreenlive.order.dto.BroadcastStockDTO;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class BroadcastRepository {

    private final EntityManager em;

    public List<BroadcastStockDTO> findAllByBroadcastScheduledTime(LocalDateTime start, LocalDateTime end) {
        String jpql = "SELECT new susussg.pengreenlive.order.dto.BroadcastStockDTO(" +
                "b.broadcastSeq, b.broadcastScheduledTime) " +
                "FROM Broadcast b WHERE b.broadcastScheduledTime BETWEEN :start AND :end";

        TypedQuery<BroadcastStockDTO> query = em.createQuery(jpql, BroadcastStockDTO.class);
        query.setParameter("start", start);
        query.setParameter("end", end);

        return query.getResultList();
    }
}
