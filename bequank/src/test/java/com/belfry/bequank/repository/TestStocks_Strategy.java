package com.belfry.bequank.repository;

import com.belfry.bequank.entity.primary.Stock;
import com.belfry.bequank.entity.primary.Strategy;
import com.belfry.bequank.repository.primary.StrategyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestStocks_Strategy {
    @Autowired
    StrategyRepository strategyRepository;

    @Test public void test1() {
        Strategy strategy = new Strategy((long) 1, "name", "2018-09-05 10:18");
        Stock stock = new Stock("stockId", "stockName", 9.0, 13.2, 0.0, 1.0, 13000, 22.4, strategy);
        strategy.setStocks(Arrays.asList(stock));

        strategy = strategyRepository.saveAndFlush(strategy);
        System.out.println(strategy.getRecordId());
    }
}
