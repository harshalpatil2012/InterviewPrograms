package com.trade.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "shares")
public class Share {

    @Id
    private String shareName;

    private int availableQuantity;
}
