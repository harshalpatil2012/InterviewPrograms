package com.trade.api.wallet.service;

public interface WalletService {
    double getBalance(String traderId);

    void updateBalance(String traderId, double amount);
}
