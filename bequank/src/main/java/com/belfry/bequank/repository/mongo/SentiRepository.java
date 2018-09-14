package com.belfry.bequank.repository.mongo;

import com.belfry.bequank.entity.mongo.CommentsInSenti;
import com.belfry.bequank.entity.mongo.Sentiment;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;

//author:andi

@Transactional
public interface SentiRepository {
    HashMap<String, Integer> getKeywords();

    //当天全部数据
    ArrayList<Sentiment> getSentiment(int type);

    ArrayList<Sentiment> getSentimentTrend(String text);

    CommentsInSenti getCommentsInSenti(String text);
}
