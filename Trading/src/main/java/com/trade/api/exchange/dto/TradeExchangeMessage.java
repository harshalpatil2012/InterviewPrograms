package com.trade.api.exchange.dto;

import com.trade.api.dto.BuyOrSell;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TradeExchangeMessage {

    private final String shareName;
    private final int quantity;
    private final double price;
    private final BuyOrSell buyOrSell;
    private final Long traderId;
}

