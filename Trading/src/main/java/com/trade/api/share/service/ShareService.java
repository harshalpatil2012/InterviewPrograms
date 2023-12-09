package com.trade.api.share.service;

import com.trade.api.dto.BuyOrSell;

public interface ShareService {
    int getAvailableQuantity(String shareName);

    void updateQuantity(String shareName, int quantity, BuyOrSell buyOrSell);
}
