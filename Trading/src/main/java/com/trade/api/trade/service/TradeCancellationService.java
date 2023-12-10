// TradeCancellationService.java
package com.trade.api.trade.service;

import com.trade.api.dto.TradeStatus;
import com.trade.api.entity.Trade;
import com.trade.api.trade.exception.TradeNotFoundException;
import com.trade.api.trade.jpa.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeCancellationService implements TradeCommand {

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeCancellationService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public void execute() {
        // Implementation for trade cancellation
    }

    public void cancelTrade(Long tradeId) {
        Trade trade = getTradeById(tradeId);
        cancelTrade(trade);
        tradeRepository.save(trade);
    }

    private Trade getTradeById(Long tradeId) {
        return tradeRepository.findByTradeId(tradeId)
                .orElseThrow(() -> new TradeNotFoundException(tradeId));
    }

    private void cancelTrade(Trade trade) {
        trade.setStatus(TradeStatus.CANCELLED);
    }
}
