package com.trade.api.trade.service;

import com.trade.api.dto.BuyOrSell;
import com.trade.api.dto.TradeReport;
import com.trade.api.dto.TradeRequest;
import com.trade.api.dto.TradeStatus;
import com.trade.api.dto.TradeUpdate;
import com.trade.api.entity.Trade;
import com.trade.api.exchange.dto.TradeExchangeMessage;
import com.trade.api.exchange.service.TradeExchangeClient;
import com.trade.api.share.service.ShareService;
import com.trade.api.trade.exception.InsufficientBalanceException;
import com.trade.api.trade.exception.InsufficientQuantityException;
import com.trade.api.trade.exception.TradeNotFoundException;
import com.trade.api.trade.jpa.TradeRepository;
import com.trade.api.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final WalletService walletService;
    private final ShareService shareService;
    private final TradeExchangeClient tradeExchangeClient;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository, WalletService walletService, ShareService shareService, TradeExchangeClient tradeExchangeClient) {
        this.tradeRepository = tradeRepository;
        this.walletService = walletService;
        this.shareService = shareService;
        this.tradeExchangeClient = tradeExchangeClient;
    }

    @Override
    public void submitTrade(TradeRequest request) {
        // 1. Validation of trade
        validateTrade(request);
        // Book new trade
        bookNewTrade(request);
    }

    @Override
    public void updateTrade(TradeUpdate update) {
        // Validate update request
        validateUpdate(update);

        // Load existing trade
        Trade trade = tradeRepository.findByTradeId(update.getTradeId()).orElseThrow(() -> new TradeNotFoundException(update.getTradeId()));

        // Apply update and update trade
        applyUpdateToTrade(trade, update);
        tradeRepository.save(trade);
    }

    @Override
    public void cancelTrade(Long tradeId) {
        // Load existing trade
        Trade trade = tradeRepository.findByTradeId(tradeId).orElseThrow(() -> new TradeNotFoundException(tradeId));

        // Cancel trade and update trade
        cancelTrade(trade);
        tradeRepository.save(trade);
    }

    @Override
    public TradeReport getTradeReport(String traderId, String keyword) {
        // Find trades based on traderId and/or keyword
        List<Trade> trades = tradeRepository.findTradesByTraderIdAndShareNameContaining(traderId, keyword);

        // Generate and return trade report
        return generateTradeReport(trades);
    }

    private TradeReport generateTradeReport(List<Trade> trades) {
        return new TradeReport();
    }

    @Override
    public Trade getTradeById(Long tradeId) {
        return tradeRepository.findByTradeId(tradeId).orElseThrow(() -> new TradeNotFoundException(tradeId));
    }

    private void validateTrade(TradeRequest request) {
        // Implement trade validation logic
        // Check share availability, wallet balance, etc.
        // Throw exception if validation fails

        if (shareService.getAvailableQuantity(request.getShareName()) < request.getQuantity()) {
            throw new InsufficientQuantityException(request.getShareName(), request.getQuantity());
        }
        if (request.getBuyOrSell() == BuyOrSell.BUY && (double) walletService.getBalance(request.getTraderId()) < calculateAmount(request)) {
            throw new InsufficientBalanceException(request.getTraderId());
        }
    }

    private void bookNewTrade(TradeRequest tradeRequest) {
        // Save trade to the database
        tradeRepository.save(tradeRequest.convertTradeRequestToDto(tradeRequest));

        // Update wallet balance and share quantity
        updateWalletAndShare(tradeRequest);

        // Send trade update to Share Exchange
        sendTradeUpdateToExchange(tradeRequest);

        // Update trade status to BOOKED
        tradeRequest.setStatus(TradeStatus.BOOKED);
        tradeRepository.save(tradeRequest.convertTradeRequestToDto(tradeRequest));
    }

    private void validateUpdate(TradeUpdate update) {
        // Implement update validation logic
        // Throw exception if validation fails
    }

    private void applyUpdateToTrade(Trade trade, TradeUpdate update) {
        // Update trade attributes based on the update request
        // Update quantity, price, buy/sell type, etc.
        trade.setQuantity(update.getQuantity());
        trade.setPrice(update.getPrice());
        trade.setBuyOrSell(update.getBuyOrSell());
        trade.setStatus(update.getStatus());
    }

    private void cancelTrade(Trade trade) {
        // Update trade status to CANCELLED
        trade.setStatus(TradeStatus.CANCELLED);
    }

    private void reverseTrade(TradeRequest tradeRequest) {
        tradeRequest.setBuyOrSell(tradeRequest.getBuyOrSell().reverse());
        updateWalletAndShare(tradeRequest);

        // Send trade update to Share Exchange
        sendTradeUpdateToExchange(tradeRequest);
    }

    private void updateWalletAndShare(TradeRequest tradeRequest) {
        walletService.updateBalance(tradeRequest.getTraderId(), calculateAmount(tradeRequest));
        shareService.updateQuantity(tradeRequest.getShareName(), tradeRequest.getQuantity(), tradeRequest.getBuyOrSell());
    }

    private double calculateAmount(TradeRequest tradeRequest) {
        return tradeRequest.getQuantity() * tradeRequest.getPrice();
    }

    private void sendTradeUpdateToExchange(TradeRequest tradeRequest) {
        // Implement logic for sending tradeRequest update to Share Exchange using tradeExchangeClient
        // This could involve sending a message to an external queue or making a REST API call

        TradeExchangeMessage message = new TradeExchangeMessage(tradeRequest.getShareName(), tradeRequest.getQuantity(), tradeRequest.getPrice(), tradeRequest.getBuyOrSell(), tradeRequest.getTraderId());
        tradeExchangeClient.sendTradeUpdate(message);
    }
}
