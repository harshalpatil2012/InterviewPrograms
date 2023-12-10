package com.trade.api.trade.exception;


public class InsufficientBalanceException extends RuntimeException {

    private final Long traderId;

    public InsufficientBalanceException(Long traderId) {
        super("Insufficient balance for trader " + traderId);
        this.traderId = traderId;
    }

    public Long getTraderId() {
        return traderId;
    }
}

