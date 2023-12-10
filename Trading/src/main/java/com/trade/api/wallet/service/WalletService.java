package com.trade.api.wallet.service;

public interface WalletService {
    double getBalance(Long traderId);

    void updateBalance(Long traderId, double amount);

}
