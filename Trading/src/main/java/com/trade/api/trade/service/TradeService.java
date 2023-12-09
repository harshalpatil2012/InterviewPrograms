package com.trade.api.trade.service;

import com.trade.api.dto.TradeReport;
import com.trade.api.dto.TradeRequest;
import com.trade.api.dto.TradeUpdate;
import com.trade.api.entity.Trade;

public interface TradeService {

    void submitTrade(TradeRequest request);

    void updateTrade(TradeUpdate update);

    void cancelTrade(Long tradeId);

    TradeReport getTradeReport(String traderId, String keyword);

    Trade getTradeById(Long tradeId);
}