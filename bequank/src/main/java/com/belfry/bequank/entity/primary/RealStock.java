package com.belfry.bequank.entity.primary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "stock")
@Getter
@Setter
@NoArgsConstructor
/**
 * 这代表真正的股票对象
 */
public class RealStock {

    @Id
    @Column(name = "code")
    private String stockId;//股票代码

    @Column(name = "name")
    private String stockName;//名字

    @Column(name = "changepercent")
    private double trend;//跌涨幅

    @Column(name = "trade")
    private double currentPrice;//现价

    private double open;//开盘价
    private double high;//最高价
    private double low;//最低价
    private double settlement;//昨日收盘价

    @Column(name = "volume")
    private double todayVolume;//成交量

    @Column(name = "turnoverratio")
    private double turnoverRate;//换手率
    private double amount;//成交额

    @Column(name = "per")
    private double marketProfitability;//市盈率

    private double pb;//市净率
    private double mktcap;//总市值
    private double nmc;//流通市值

    public RealStock(String stockId, String stockName, double trend, double currentPrice, double open, double high, double low, double settlement, double todayVolume, double turnoverRate, double amount, double marketProfitability, double pb, double mktcap, double nmc) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.trend = trend;
        this.currentPrice = currentPrice;
        this.open = open;
        this.high = high;
        this.low = low;
        this.settlement = settlement;
        this.todayVolume = todayVolume;
        this.turnoverRate = turnoverRate;
        this.amount = amount;
        this.marketProfitability = marketProfitability;
        this.pb = pb;
        this.mktcap = mktcap;
        this.nmc = nmc;
    }
}
