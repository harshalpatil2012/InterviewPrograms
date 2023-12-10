package com.trade.api.wallet.jpa;

import com.trade.api.entity.Wallet;

import java.util.Optional;

public interface WalletRepository {
    Optional<Wallet> findById(Long traderId);

    Wallet save(Wallet wallet);
}
