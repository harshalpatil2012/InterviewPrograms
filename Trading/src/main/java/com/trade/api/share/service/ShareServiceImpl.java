package com.trade.api.share.service;

import com.trade.api.dto.BuyOrSell;
import com.trade.api.dto.TradeRequest;
import com.trade.api.entity.Share;
import com.trade.api.exchange.dto.TradeExchangeMessage;
import com.trade.api.exchange.service.TradeExchangeClient;
import com.trade.api.share.exception.ShareNotFoundException;
import com.trade.api.share.jpa.ShareRepository;
import com.trade.api.trade.exception.InsufficientBalanceException;
import com.trade.api.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceImpl implements ShareService {

    private final ShareRepository shareRepository;
    private final TradeExchangeClient tradeExchangeClient;
    private final WalletService walletService;


    @Autowired
    public ShareServiceImpl(ShareRepository shareRepository, TradeExchangeClient tradeExchangeClient,
                            WalletService walletService) {
        this.shareRepository = shareRepository;
        this.tradeExchangeClient = tradeExchangeClient;
        this.walletService = walletService;
    }

    @Override
    public int getAvailableQuantity(String shareName) {
        Share share = shareRepository.findById(shareName)
                .orElseThrow(() -> new ShareNotFoundException(shareName));
        return share.getAvailableQuantity();
    }

    @Override
    public void updateQuantity(TradeRequest tradeRequest) {
        String shareName = tradeRequest.getShareName();
        int quantity = tradeRequest.getQuantity();
        Share share = shareRepository.findById(shareName)
                .orElseThrow(() -> new ShareNotFoundException(shareName));

        if (tradeRequest.getBuyOrSell() == BuyOrSell.BUY) {
            share.setAvailableQuantity(share.getAvailableQuantity() - quantity);
            blockAmountForBuy(tradeRequest.getTraderId(), tradeRequest.getPrice());
        } else {
            share.setAvailableQuantity(share.getAvailableQuantity() + quantity);
        }
        // Send order to exchange the share quantity pass shareName, availableQuantity - quantity
        TradeExchangeMessage exchangeMessage = new TradeExchangeMessage();
        tradeExchangeClient.updateShareQuantity(exchangeMessage);
        shareRepository.save(share);
    }


    // Method to block amount in wallet for a buy trade
    public void blockAmountForBuy(Long traderId, double amount) {
        // Get the current wallet balance
        double balance = walletService.getBalance(traderId);

        // Check if there is sufficient balance to block
        if (balance < amount) {
            throw new InsufficientBalanceException(traderId);
        }

        // Block the amount in the wallet
        walletService.updateBalance(traderId, balance - amount);

        // Note: We may want to implement additional logic, such as updating a dedicated
        // table to track blocked amounts for pending buy trades.
    }
}