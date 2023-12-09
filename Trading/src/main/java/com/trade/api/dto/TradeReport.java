package com.trade.api.dto;

import lombok.Data;

@Data
public class TradeReport {

    private Long tradeId;
    private String shareName;
    private int quantity;
    private double price;
    private BuyOrSell buyOrSell;
    private TradeStatus status;
}
