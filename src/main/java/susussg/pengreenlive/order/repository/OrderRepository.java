package susussg.pengreenlive.order.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import susussg.pengreenlive.order.domain.Order;

@RequiredArgsConstructor
@Repository
public class OrderRepository {
    final private EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }
}
