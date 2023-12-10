package com.trade.api.trade.service;

import com.trade.api.dto.TradeRequest;

public interface TradeService {
    void submitTrade(TradeRequest request);

    void updateTrade(TradeRequest updateRequest);

    void cancelTrade(Long tradeId);

}
