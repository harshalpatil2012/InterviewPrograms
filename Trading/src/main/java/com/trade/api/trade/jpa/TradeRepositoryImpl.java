package com.trade.api.trade.jpa;

import com.trade.api.entity.Trade;
import com.trade.api.trade.exception.TradeNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TradeRepositoryImpl implements TradeRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Long save(Trade trade) {
        entityManager.persist(trade);
        return trade.getTradeId();
    }

    @Override
    public List<Trade> findTradesByTraderId(String traderId) {
        CriteriaQuery<Trade> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Trade.class);
        Root<Trade> root = criteriaQuery.from(Trade.class);
        criteriaQuery.select(root);
        criteriaQuery.where(entityManager.getCriteriaBuilder().equal(root.get("traderId"), traderId));
        TypedQuery<Trade> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
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
    public Optional<Trade> findByTradeId(Long tradeId) {
        Trade trade = entityManager.find(Trade.class, tradeId);
        if (trade == null) {
            throw new TradeNotFoundException(tradeId);
        }
        return Optional.of(trade);
    }

    @Override
    public List<Trade> findAll() {
        CriteriaQuery<Trade> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Trade.class);
        Root<Trade> root = criteriaQuery.from(Trade.class);
        criteriaQuery.select(root);
        TypedQuery<Trade> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void deleteTrade(Trade trade) {
        entityManager.remove(entityManager.contains(trade) ? trade : entityManager.merge(trade));
    }

    @Override
    public boolean existsById(Long tradeId) {
        return entityManager.find(Trade.class, tradeId) != null;
    }

    @Override
    public List<Trade> findTradesByTraderIdAndShareNameContaining(String traderId, String shareName) {
        CriteriaQuery<Trade> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Trade.class);
        Root<Trade> root = criteriaQuery.from(Trade.class);
        criteriaQuery.select(root);
        Predicate traderIdPredicate = entityManager.getCriteriaBuilder().equal(root.get("traderId"), traderId);
        Predicate shareNamePredicate = entityManager.getCriteriaBuilder().like(root.get("shareName"), "%" + shareName + "%");
        criteriaQuery.where(entityManager.getCriteriaBuilder().and(traderIdPredicate, shareNamePredicate));
        TypedQuery<Trade> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<Trade> findAllByTraderId(String traderId) {
        CriteriaQuery<Trade> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Trade.class);
        Root<Trade> root = criteriaQuery.from(Trade.class);
        criteriaQuery.select(root);
        criteriaQuery.where(entityManager.getCriteriaBuilder().equal(root.get("traderId"), traderId));
        TypedQuery<Trade> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

}
