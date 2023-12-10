package com.trade.api.trade.exception;

public class TradeNotFoundException extends RuntimeException {

    private final Long tradeId;

    public TradeNotFoundException(Long tradeId) {
        super("Trade with ID " + tradeId + " not found");
        this.tradeId = tradeId;
    }

    public Long getTradeId() {
        return tradeId;
    }
}
