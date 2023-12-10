package com.trade.api.trade.jpa;

import com.trade.api.entity.Trade;
import com.trade.api.trade.exception.TradeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TradeRepository {

    void save(Trade trade);

    Optional<Trade> findByTradeId(Long tradeId) throws TradeNotFoundException;

    List<Trade> findByShareNameContaining(String shareName);

    boolean existsById(Long tradeId);
}