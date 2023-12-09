package com.trade.api.share.jpa;

import com.trade.api.entity.Share;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ShareRepositoryImpl implements ShareRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Share> findById(String shareName) {
        TypedQuery<Share> query = entityManager.createQuery(
                "SELECT s FROM Share s WHERE s.shareName = :shareName", Share.class
        );
        query.setParameter("shareName", shareName);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Share save(Share share) {
        entityManager.persist(share);
        return share;
    }
}
