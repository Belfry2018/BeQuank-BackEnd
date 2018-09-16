package com.belfry.bequank.entity.primary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Strategy {
    @Id
    @GeneratedValue
    private long recordId;
    private long userId;
    private String recordName;
    private String recordTime;
    @OneToMany(targetEntity = StrategyItem.class, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "strategy")
    private List<StrategyItem> stocks = new ArrayList<>();

    public Strategy(long userId, String recordName, String recordTime) {
        this.userId = userId;
        this.recordName = recordName;
        this.recordTime = recordTime;
    }

    public void add(StrategyItem item) {
        assertNotNull(stocks);
        stocks.add(item);
    }
}
