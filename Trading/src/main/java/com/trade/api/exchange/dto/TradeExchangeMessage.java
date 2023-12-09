package com.trade.api.exchange.dto;

import com.trade.api.dto.BuyOrSell;

public class TradeExchangeMessage {

    private final String shareName;
    private final int quantity;
    private final double price;
    private final BuyOrSell buyOrSell;
    private final String traderId;

    public TradeExchangeMessage(String shareName, int quantity, double price, BuyOrSell buyOrSell, String traderId) {
        this.shareName = shareName;
        this.quantity = quantity;
        this.price = price;
        this.buyOrSell = buyOrSell;
        this.traderId = traderId;
    }

    public String getShareName() {
        return shareName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public BuyOrSell getBuyOrSell() {
        return buyOrSell;
    }

    public String getTraderId() {
        return traderId;
    }
}

