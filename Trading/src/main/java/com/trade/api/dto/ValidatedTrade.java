package com.trade.api.dto;

import lombok.Data;

@Data
public class ValidatedTrade {

    private Long tradeId;

    private String shareName;

    private int quantity;

    private double price;

    private String traderId;

    private BuyOrSell buyOrSell;
}