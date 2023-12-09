package com.trade.api.dto;

import com.trade.api.entity.Trade;
import lombok.Data;

@Data
public class TradeReport {

    private Long tradeId;
    private String shareName;
    private int quantity;
    private double price;
    private BuyOrSell buyOrSell;
    private TradeStatus status;

    public static TradeReport fromTrade(Trade trade) {
        TradeReport report = new TradeReport();
        report.setTradeId(trade.getTradeId());
        report.setShareName(trade.getShareName());
        report.setQuantity(trade.getQuantity());
        report.setPrice(trade.getPrice());
        report.setBuyOrSell(trade.getBuyOrSell());
        report.setStatus(trade.getStatus());
        return report;
    }
}
