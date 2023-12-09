package com.trade.api.trade.jpa;

import com.trade.api.entity.Trade;
import com.trade.api.trade.exception.TradeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository {

    Long save(Trade trade);

    List<Trade> findAll();

    Optional<Trade> findByTradeId(Long tradeId) throws TradeNotFoundException;

    List<Trade> findTradesByTraderId(String traderId);

    List<Trade> findByShareNameContaining(String shareName);

    void deleteTrade(Trade trade);

    boolean existsById(Long tradeId);

    List<Trade> findTradesByTraderIdAndShareNameContaining(String traderId, String shareName);

    List<Trade> findAllByTraderId(String traderId);
}