package susussg.pengreenlive.order.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import susussg.pengreenlive.order.domain.Broadcast;
import susussg.pengreenlive.order.domain.Channel;
import susussg.pengreenlive.order.domain.Order;

@Slf4j
@RequiredArgsConstructor
@Repository
public class OrderRepository {
    final private EntityManager em;

    public void save(Order order) {
        Broadcast broadcast = em.find(Broadcast.class, order.getBroadcast().getBroadcastSeq());
        order.setBroadcast(broadcast);
        Channel channel = em.find(Channel.class, broadcast.getChannelSeq());
        log.info("channel = {}", channel);
        order.setChannel(channel);
        order.setVendorSeq(channel.getVendorSeq());
        log.info("order = {}", order);

        em.persist(order);
    }



}
