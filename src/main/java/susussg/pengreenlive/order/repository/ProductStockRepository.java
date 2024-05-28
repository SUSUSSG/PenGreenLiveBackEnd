package susussg.pengreenlive.order.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import susussg.pengreenlive.order.domain.Product;
import susussg.pengreenlive.order.domain.ProductStock;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProductStockRepository {

    private final EntityManager em;

    public void update(Product product) {
        em.merge(product);
    }

    public Optional<ProductStock> findByProduct_ProductSeq(Long productSeq) {
        String jpql = "SELECT ps FROM ProductStock ps WHERE ps.product.productSeq = :productSeq";
        TypedQuery<ProductStock> query = em.createQuery(jpql, ProductStock.class);
        query.setParameter("productSeq", productSeq);
        return query.getResultList().stream().findFirst();
    }

    public List<ProductStock> findAllByBroadcastId(Long broadcastId) {
        String jpql = "SELECT ps FROM ProductStock ps JOIN ps.product p JOIN p.broadcasts b WHERE b.broadcastSeq = :broadcastId";
        TypedQuery<ProductStock> query = em.createQuery(jpql, ProductStock.class);
        query.setParameter("broadcastId", broadcastId);
        return query.getResultList();
    }

    public void save(ProductStock productStock) {
        if (productStock.getProduct() != null && productStock.getProduct().getProductSeq() != 0) {
            em.merge(productStock);
        } else {
            em.persist(productStock);
        }
    }
}
