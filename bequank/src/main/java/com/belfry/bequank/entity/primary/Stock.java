package com.belfry.bequank.entity.primary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "rcm_stock")
@Getter
@Setter
@NoArgsConstructor
/**
 * Stock不是现实股票对象的反映，而是一个策略的一个条目
 */
public class Stock {
    @Id
    @GeneratedValue
    private long id;
    private String stockId;//股票代码
    private String stockName;//股票名字
    private double currentPrice;//股价
    private double trend;//跌涨幅%
    private double turnoverRate;//累计换手率%
    private double marketProfitability;//股市盈利率%
    private long todayVolume;//今日成交量
    private double buyRate;//推荐购买比例

    public Stock(String stockId, String stockName, double currentPrice, double trend, double turnoverRate, double marketProfitability, long todayVolume, double buyRate) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.currentPrice = currentPrice;
        this.trend = trend;
        this.turnoverRate = turnoverRate;
        this.marketProfitability = marketProfitability;
        this.todayVolume = todayVolume;
        this.buyRate = buyRate;
    }

    public static Stock transform(RealStock rs){
        return new Stock(rs.getStockId(), rs.getStockName(), rs.getCurrentPrice(), rs.getTrend(), rs.getTurnoverRate(),
                rs.getMarketProfitability(), new Double(rs.getTodayVolume()).longValue(), 0.0);
    }

}
