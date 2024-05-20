package susussg.pengreenlive.order.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import susussg.pengreenlive.order.domain.Product;
import susussg.pengreenlive.order.domain.ProductStock;
import susussg.pengreenlive.order.dto.ProductStockDTO;

@RequiredArgsConstructor
@Repository
public class ProductStockRepository {

    final private EntityManager em;

    public void update(Product product)
    {
        em.persist(product);
    }

//    public ProductStockDTO selectByProductSeq(ProductStock productStock) {
//        return em.find(ProductStock.class, productStock.getProduct().getProductSeq());
//    }
}
