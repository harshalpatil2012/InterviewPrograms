package com.trade.api.wallet.service.exception;

public class WalletNotFoundException extends RuntimeException {
    private final String traderId;

    public WalletNotFoundException(String traderId) {
        super("Wallet for trader ID '" + traderId + "' not found");
        this.traderId = traderId;
    }

    public String getTraderId() {
        return traderId;
    }
}
