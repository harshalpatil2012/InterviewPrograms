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
    public TradeServiceImpl(
            TradeRepository tradeRepository,
            WalletService walletService,
            ShareService shareService,
            TradeExchangeClient tradeExchangeClient
    ) {
        this.tradeRepository = tradeRepository;
        this.walletService = walletService;
        this.shareService = shareService;
        this.tradeExchangeClient = tradeExchangeClient;
    }

    @Override
    public void submitTrade(TradeRequest request) {
        validateTrade(request);
        bookNewTrade(request);
    }

    @Override
    public void updateTrade(TradeUpdate update) {
        validateUpdate(update);
        Trade trade = tradeRepository.findByTradeId(update.getTradeId())
                .orElseThrow(() -> new TradeNotFoundException(update.getTradeId()));
        applyUpdateToTrade(trade, update);
        tradeRepository.save(trade);
    }

    @Override
    public void cancelTrade(Long tradeId) {
        Trade trade = tradeRepository.findByTradeId(tradeId)
                .orElseThrow(() -> new TradeNotFoundException(tradeId));
        cancelTrade(trade);
        tradeRepository.save(trade);
    }

    @Override
    public TradeReport getTradeReport(String traderId, String keyword) {
        List<Trade> trades = tradeRepository.findTradesByTraderIdAndShareNameContaining(traderId, keyword);
        return generateTradeReport(trades);
    }

    private TradeReport generateTradeReport(List<Trade> trades) {
        // Implement the logic to generate a meaningful trade report
        // ...
        return new TradeReport();
    }

    private void validateTrade(TradeRequest request) {
        if (shareService.getAvailableQuantity(request.getShareName()) < request.getQuantity()) {
            throw new InsufficientQuantityException(request.getShareName(), request.getQuantity());
        }
        if (request.getBuyOrSell() == BuyOrSell.BUY &&
                walletService.getBalance(request.getTraderId()) < calculateAmount(request)) {
            throw new InsufficientBalanceException(request.getTraderId());
        }
    }

    private void bookNewTrade(TradeRequest tradeRequest) {
        Trade trade = tradeRequest.toTrade(tradeRequest);
        tradeRepository.save(trade);
        updateWalletAndShare(tradeRequest);
        sendTradeUpdateToExchange(tradeRequest);
        trade.setStatus(TradeStatus.BOOKED);
        tradeRepository.save(trade);
    }

    private void validateUpdate(TradeUpdate update) {
        // Implement update validation logic
        // ...
    }

    private void applyUpdateToTrade(Trade trade, TradeUpdate update) {
        trade.setQuantity(update.getQuantity());
        trade.setPrice(update.getPrice());
        trade.setBuyOrSell(update.getBuyOrSell());
        trade.setStatus(update.getStatus());
    }

    private void cancelTrade(Trade trade) {
        trade.setStatus(TradeStatus.CANCELLED);
    }

    private void updateWalletAndShare(TradeRequest tradeRequest) {
        walletService.updateBalance(tradeRequest.getTraderId(), calculateAmount(tradeRequest));
        shareService.updateQuantity(tradeRequest.getShareName(), tradeRequest.getQuantity(), tradeRequest.getBuyOrSell());
    }

    private double calculateAmount(TradeRequest tradeRequest) {
        return tradeRequest.getQuantity() * tradeRequest.getPrice();
    }

    private void sendTradeUpdateToExchange(TradeRequest tradeRequest) {
        TradeExchangeMessage message = new TradeExchangeMessage(
                tradeRequest.getShareName(),
                tradeRequest.getQuantity(),
                tradeRequest.getPrice(),
                tradeRequest.getBuyOrSell(),
                tradeRequest.getTraderId()
        );
        tradeExchangeClient.sendTradeUpdate(message);
    }
}