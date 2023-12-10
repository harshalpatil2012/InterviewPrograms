// TradeServiceImpl.java
package com.trade.api.trade.service;

import com.trade.api.dto.TradeRequest;
import com.trade.api.entity.Trade;
import com.trade.api.trade.exception.TradeNotFoundException;
import com.trade.api.trade.jpa.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    private final List<TradeCommand> tradeCommands;

    TradeRepository tradeRepository;

    TradeValidationService tradeValidationService;
    TradeBookingService bookingService;
    TradeCancellationService tradeCancellationService;

    @Autowired
    public TradeServiceImpl(List<TradeCommand> tradeCommands, TradeRepository tradeRepository,
                            TradeValidationService tradeValidationService, TradeBookingService bookingService, TradeCancellationService tradeCancellationService) {
        this.tradeCommands = tradeCommands;
        this.tradeRepository = tradeRepository;
        this.tradeValidationService = tradeValidationService;
        this.bookingService = bookingService;
        this.tradeCancellationService = tradeCancellationService;
    }

    @Override
    public void submitTrade(TradeRequest request) {
        executeCommand(() -> validateAndBookTrade(request));
    }

    @Override
    public void updateTrade(TradeRequest updateRequest) {
        executeCommand(() -> validateAndUpdateTrade(updateRequest));
    }

    @Override
    public void cancelTrade(Long tradeId) {
        executeCommand(() -> validateAndCancelTrade(tradeId));
    }


    private void executeCommand(TradeCommand command) {
        command.execute();
    }


    private void validateAndBookTrade(TradeRequest request) {
        tradeValidationService.validateTrade(request);
        bookingService.bookTrade(request);
    }

    private void validateAndUpdateTrade(TradeRequest updateRequest) {
        tradeValidationService.validateTrade(updateRequest);
        Trade trade = tradeRepository.findByTradeId(updateRequest.getTradeId())
                .orElseThrow(() -> new TradeNotFoundException(updateRequest.getTradeId()));
        tradeRepository.save(trade);
    }

    private void validateAndCancelTrade(Long tradeId) {
        Trade trade = tradeRepository.findByTradeId(tradeId)
                .orElseThrow(() -> new TradeNotFoundException(tradeId));
        tradeCancellationService.cancelTrade(trade.getTradeId());
    }
}