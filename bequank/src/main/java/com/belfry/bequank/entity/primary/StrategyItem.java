package com.belfry.bequank.entity.primary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StrategyItem {
    @Id
    @GeneratedValue
    private long id;
    private String stockId;
    private double buyRate;
    @ManyToOne(targetEntity = Strategy.class, optional = true)
    private Strategy strategy;

    public StrategyItem(String stockId, double buyRate, Strategy strategy) {
        this.stockId = stockId;
        this.buyRate = buyRate;
        this.strategy = strategy;
    }
}
