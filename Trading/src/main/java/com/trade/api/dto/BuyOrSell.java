package com.trade.api.dto;

public enum BuyOrSell {
    BUY, SELL;

    public BuyOrSell reverse() {
        switch (this) {
            case BUY:
                return SELL;
            case SELL:
                return BUY;
            default:
                throw new IllegalStateException("Unexpected BuyOrSell value: " + this);
        }
    }
}
