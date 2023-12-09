package com.trade.api.trade.exception;


public class InsufficientBalanceException extends RuntimeException {

    private final String traderId;

    public InsufficientBalanceException(String traderId) {
        super("Insufficient balance for trader " + traderId);
        this.traderId = traderId;
    }

    public String getTraderId() {
        return traderId;
    }
}

