// TradeValidationService.java
package com.trade.api.trade.service;

import com.trade.api.dto.BuyOrSell;
import com.trade.api.dto.TradeRequest;
import com.trade.api.share.service.ShareService;
import com.trade.api.trade.exception.InsufficientBalanceException;
import com.trade.api.trade.exception.InsufficientQuantityException;
import com.trade.api.trade.exception.TradeNotFoundException;
import com.trade.api.trade.jpa.TradeRepository;
import com.trade.api.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeValidationService implements TradeCommand {

    private final ShareService shareService;
    private final WalletService walletService;
    TradeRepository tradeRepository;

    @Autowired
    public TradeValidationService(ShareService shareService, WalletService walletService, TradeRepository tradeRepository) {
        this.shareService = shareService;
        this.walletService = walletService;
        this.tradeRepository = tradeRepository;
    }

    @Override
    public void execute() {
        // Implementation for trade validation
    }

    public void validateTrade(TradeRequest request) {
        if (request.getTradeId() != null && tradeRepository.existsById(Long.valueOf(request.getTradeId()))) {
            throw new TradeNotFoundException(request.getTradeId());
        }

        if (shareService.getAvailableQuantity(request.getShareName()) < request.getQuantity()) {
            throw new InsufficientQuantityException(request.getShareName(), request.getQuantity());
        }

        double requiredAmount = calculateAmount(request);
        if (request.getBuyOrSell() == BuyOrSell.BUY && walletService.getBalance(request.getTraderId()) < requiredAmount) {
            throw new InsufficientBalanceException(request.getTraderId());
        }
    }

    private double calculateAmount(TradeRequest tradeRequest) {
        return tradeRequest.getQuantity() * tradeRequest.getPrice();
    }
}
