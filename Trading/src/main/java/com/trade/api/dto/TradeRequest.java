package com.trade.api.dto;

import com.trade.api.entity.Trade;
import lombok.Data;

@Data
public class TradeRequest {

    private String shareName;
    private int quantity;
    private double price;
    private String tradeId;
    private String traderId;
    private BuyOrSell buyOrSell;

    public void setStatus(TradeStatus tradeStatus) {
    }
    public Trade toTrade(TradeRequest request) {
        Trade dto = new Trade();
        dto.setShareName(request.getShareName());
        dto.setQuantity(request.getQuantity());
        dto.setPrice(request.getPrice());
        dto.setTraderId(request.getTraderId());
        dto.setBuyOrSell(BuyOrSell.valueOf(request.getBuyOrSell().name()));
        dto.setStatus(TradeStatus.valueOf(TradeStatus.NEW.name()));
        return dto;
    }
}
