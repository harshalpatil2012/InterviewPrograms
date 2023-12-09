package com.trade.api.entity;

import com.trade.api.dto.BuyOrSell;
import com.trade.api.dto.TradeStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Trade {

    @Id
    private Long tradeId;
    private String shareName;
    private int quantity;
    private double price;
    private String traderId;
    private TradeStatus status;
    private BuyOrSell buyOrSell;

}

