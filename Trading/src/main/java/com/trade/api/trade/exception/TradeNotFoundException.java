package com.trade.api.trade.exception;

public class TradeNotFoundException extends RuntimeException {

    private final String tradeId;

    public TradeNotFoundException(String tradeId) {
        super("Trade with ID " + tradeId + " not found");
        this.tradeId = tradeId;
    }

    public String getTradeId() {
        return tradeId;
    }
}
