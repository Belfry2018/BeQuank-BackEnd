package com.belfry.bequank.entity.primary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StrategyVO extends StrategyItem {

    private String stockId;
    private String stockName;
    private double buyrate;
    private double currentPrice;
    private double trend;
    private double turnoverRate;
    private double marketProfitability;
    private double todayVolume;
    
    public StrategyVO(StrategyItem item, RealStock stock) {
        this.buyrate = item.getBuyRate();
        this.stockId = item.getStockId();
        this.stockName = stock.getStockName();
        this.currentPrice = stock.getCurrentPrice();
        this.trend = stock.getTrend();
        this.turnoverRate = stock.getTurnoverRate();
        this.todayVolume = stock.getTodayVolume();
        this.marketProfitability = stock.getMarketProfitability();
    }
}
