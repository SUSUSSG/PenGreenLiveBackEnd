package susussg.pengreenlive.user.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import susussg.pengreenlive.user.domain.LocalLogin;
import susussg.pengreenlive.user.domain.SocialLogin;
import susussg.pengreenlive.user.domain.TbUser;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;

    public void insertUser(TbUser user) {
        em.persist(user);
    }

    public void insertLocalLogin(LocalLogin localLogin) {
        em.persist(localLogin);
    }

    public void insertSocialLogin(SocialLogin socialLogin) {
        em.persist(socialLogin);
    }
}
