package susussg.pengreenlive.order.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import susussg.pengreenlive.order.domain.Product;
import susussg.pengreenlive.order.domain.ProductStock;
import susussg.pengreenlive.order.dto.ProductStockDTO;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ProductStockRepository {

    private final EntityManager em;

    public void update(Product product) {
        em.merge(product);
    }

    public Optional<ProductStockDTO> findByProduct_ProductSeq(Long productSeq) {
        String jpql = "SELECT new susussg.pengreenlive.order.dto.ProductStockDTO(ps.product.productSeq, ps.productStock) " +
                "FROM ProductStock ps WHERE ps.product.productSeq = :productSeq";
        TypedQuery<ProductStockDTO> query = em.createQuery(jpql, ProductStockDTO.class);
        query.setParameter("productSeq", productSeq);
        return query.getResultList().stream().findFirst();
    }

    public List<ProductStockDTO> findAllByBroadcastSeqIn(List<Long> broadcastSeqs) {
        log.info("방송 번호 {}", broadcastSeqs);
        String jpql = "SELECT new susussg.pengreenlive.order.dto.ProductStockDTO(bp.productSeq, bp.broadcastSeq, ps.productStock) " +
                "FROM TB_BROADCAST_PRODUCT bp " +
                "JOIN ProductStock ps ON bp.productSeq = ps.product.productSeq " +
                "WHERE bp.broadcastSeq IN :broadcastSeqs";
        TypedQuery<ProductStockDTO> query = em.createQuery(jpql, ProductStockDTO.class);
        query.setParameter("broadcastSeqs", broadcastSeqs);
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
