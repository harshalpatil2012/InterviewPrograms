package com.trade.api.share.service;

import com.trade.api.dto.TradeRequest;

public interface ShareService {
    int getAvailableQuantity(String shareName);

    void updateQuantity(TradeRequest tradeRequest);
}
