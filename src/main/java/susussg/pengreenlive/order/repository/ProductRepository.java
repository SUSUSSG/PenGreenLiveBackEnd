package susussg.pengreenlive.order.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import susussg.pengreenlive.order.domain.Order;
import susussg.pengreenlive.order.domain.Product;

@RequiredArgsConstructor
@Repository
public class ProductRepository {

    final private EntityManager em;

    public void update(Product product)
    {
        em.persist(product);
    }
}
