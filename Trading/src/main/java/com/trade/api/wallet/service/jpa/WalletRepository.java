package com.trade.api.wallet.service.jpa;

import com.trade.api.entity.Wallet;

import java.util.Optional;

public interface WalletRepository {

    Optional<Wallet> findById(String traderId);

    Wallet save(Wallet wallet);
}
