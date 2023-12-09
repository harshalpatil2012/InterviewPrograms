package com.trade.api.exchange.service;

public class TradeExchangeException extends RuntimeException {
    public TradeExchangeException(String message) {
        super(message);
    }

    public TradeExchangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
