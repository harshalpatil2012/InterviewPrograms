package com.trade.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Wallet {

    @Id
    private String traderId;

    private double balance;
}

