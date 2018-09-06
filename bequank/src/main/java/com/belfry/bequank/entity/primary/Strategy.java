package com.belfry.bequank.entity.primary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(targetEntity = Stock.class, cascade = {CascadeType.MERGE, CascadeType.REMOVE,CascadeType.PERSIST}, fetch = FetchType.EAGER, mappedBy = "strategy")
    private List<Stock> stocks;


    public Strategy(long userId, String recordName, String recordTime) {
        this.userId = userId;
        this.recordName = recordName;
        this.recordTime = recordTime;
        this.stocks = new ArrayList<>();
    }
}
