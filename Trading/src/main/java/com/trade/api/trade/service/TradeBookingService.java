// TradeBookingService.java
package com.trade.api.trade.service;

import com.trade.api.dto.TradeRequest;
import com.trade.api.dto.TradeStatus;
import com.trade.api.entity.Trade;
import com.trade.api.exchange.dto.TradeExchangeMessage;
import com.trade.api.exchange.service.TradeExchangeClient;
import com.trade.api.share.service.ShareService;
import com.trade.api.trade.jpa.TradeRepository;
import com.trade.api.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeBookingService implements TradeCommand {

    private final TradeRepository tradeRepository;
    private final WalletService walletService;
    private final ShareService shareService;
    private final TradeExchangeClient tradeExchangeClient;

    @Autowired
    public TradeBookingService(
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
    public void execute() {
        // Implementation for trade booking
    }

    // In case of Buy, check wallet balance in Broker system
    //	In case of Sell, check for number of ShareQuantity in Broker system
    public void bookTrade(TradeRequest tradeRequest) {
        Trade trade = tradeRequest.toTrade(tradeRequest);
        tradeRepository.save(trade);

        updateWalletAndShare(tradeRequest);
        sendTradeUpdateToExchange(tradeRequest);

        trade.setStatus(TradeStatus.BOOKED);
        tradeRepository.save(trade);
    }

    // In case of Buy, block/release delta amount in wallet
    private void updateWalletAndShare(TradeRequest tradeRequest) {
        walletService.updateBalance(tradeRequest.getTraderId(), calculateAmount(tradeRequest));
        shareService.updateQuantity(tradeRequest);
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
