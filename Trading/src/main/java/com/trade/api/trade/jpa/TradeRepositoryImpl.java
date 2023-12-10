package com.trade.api.trade.jpa;

import com.trade.api.entity.Trade;
import com.trade.api.trade.exception.TradeNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TradeRepositoryImpl implements TradeRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Trade> findByTradeId(Long tradeId) {
        Trade trade = entityManager.find(Trade.class, tradeId);
        if (trade == null) {
            throw new TradeNotFoundException(tradeId);
        }
        return Optional.of(trade);
    }

    @Override
    public List<Trade> findByShareNameContaining(String shareName) {
        CriteriaQuery<Trade> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Trade.class);
        Root<Trade> root = criteriaQuery.from(Trade.class);
        criteriaQuery.select(root);
        criteriaQuery.where(entityManager.getCriteriaBuilder().like(root.get("shareName"), "%" + shareName + "%"));
        TypedQuery<Trade> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void save(Trade trade) {
        entityManager.persist(trade);
        trade.getTradeId();
    }

    @Override
    public List<Trade> findTradesByTraderIdAndShareNameContaining(String traderId, String shareName) {
        TypedQuery<Trade> query = entityManager.createQuery(
                "SELECT t FROM Trade t WHERE t.traderId = :traderId AND t.shareName LIKE :shareName",
                Trade.class
        );
        query.setParameter("traderId", traderId);
        query.setParameter("shareName", "%" + shareName + "%");
        return query.getResultList();
    }

    @Override
    public boolean existsById(Long tradeId) {
        return entityManager.find(Trade.class, tradeId) != null;
    }
}
