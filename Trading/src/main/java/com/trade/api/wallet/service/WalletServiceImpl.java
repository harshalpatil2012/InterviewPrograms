package com.trade.api.wallet.service;

import com.trade.api.entity.Wallet;
import com.trade.api.wallet.exception.WalletNotFoundException;
import com.trade.api.wallet.jpa.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public double getBalance(Long traderId) {
        Wallet wallet = walletRepository.findById(traderId)
                .orElseThrow(() -> new WalletNotFoundException(traderId));
        return wallet.getBalance();
    }

    @Override
    public void updateBalance(Long traderId, double amount) {
        Wallet wallet = walletRepository.findById(traderId)
                .orElseThrow(() -> new WalletNotFoundException(traderId));
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);
    }
}