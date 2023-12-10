package com.trade.api.wallet.jpa;

import com.trade.api.entity.Wallet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class WalletRepositoryImpl implements WalletRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Wallet> findById(Long traderId) {
        TypedQuery<Wallet> query = entityManager.createQuery("SELECT w FROM Wallet w WHERE w.traderId = :traderId", Wallet.class);
        query.setParameter("traderId", traderId);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Wallet save(Wallet wallet) {
        entityManager.persist(wallet);
        return wallet;
    }
}
