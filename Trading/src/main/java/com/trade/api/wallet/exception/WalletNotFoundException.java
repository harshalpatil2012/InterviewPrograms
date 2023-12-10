package com.trade.api.wallet.exception;

public class WalletNotFoundException extends RuntimeException {
    private final Long traderId;

    public WalletNotFoundException(Long traderId) {
        super("Wallet for trader ID '" + traderId + "' not found");
        this.traderId = traderId;
    }

    public Long getTraderId() {
        return traderId;
    }
}
