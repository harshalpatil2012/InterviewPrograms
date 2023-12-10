package com.trade.api.exchange.service;

import com.trade.api.exchange.dto.TradeExchangeMessage;

public interface TradeExchangeClient {

    void sendTradeUpdate(TradeExchangeMessage message);


    void updateShareQuantity(TradeExchangeMessage message);
}
