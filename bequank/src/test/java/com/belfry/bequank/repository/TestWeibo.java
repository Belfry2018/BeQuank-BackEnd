package com.belfry.bequank.repository;

import com.belfry.bequank.entity.mongo.Posting;
import com.belfry.bequank.entity.mongo.Sentiment;
import com.belfry.bequank.repository.mongo.PostingRepository;
import com.belfry.bequank.repository.mongo.SentiRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//author : andi

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestWeibo {
    @Autowired
    private PostingRepository postingRepository;
    @Autowired
    private SentiRepository sentiRepository;

    @Test
    public void getPostings() {
        Posting posting;
        int i = 0;
        int page = 1;
        int count = 8;
        List<Posting> list = postingRepository.getHotSpots(page, count);
        Assert.notNull(list);
        while (i < count) {
            posting = list.get(i);
            i++;
            System.out.println(posting.getTime() + " " + posting);
        }

    }

    @Test
    public void getKeyWords() {
        HashMap<String, Integer> map = sentiRepository.getKeywords();
        Assert.notNull(map);
        System.out.println(map.size());
        int i = 1;
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue() + " " + i);
            i++;
        }
        System.out.println(map.get("特朗普"));
    }

    @Test
    public void getBadSentiments() {
        ArrayList<Sentiment> sentiments = sentiRepository.getSentiment(0);
        Assert.notNull(sentiments);//2.5s 0906
        System.out.println(sentiments.size());
    }

    @Test
    public void getGoodSentiments() {
        ArrayList<Sentiment> sentiments = sentiRepository.getSentiment(1);
        Assert.notNull(sentiments);
        //print first ten
        for (int i = 0; i < 10; i++) {
            System.out.println(sentiments.get(i));
        }
    }

    @Test
    public void getNormalSentiTrend() {
        getSentiTrend("黄金");
        getSentiTrend("京东");
    }

    @Test
    public void getNullSentiTrend() {
        getSentiTrend("");
        getSentiTrend(null);
    }

    public void getSentiTrend(String text) {
        ArrayList<Sentiment> sentimentTrend = sentiRepository.getSentimentTrend(text);
        Assert.notNull(sentimentTrend);
        System.out.println(sentimentTrend.size());
        int i = 0;
        final int DAYS_IN_A_WEEK = 7;
        while (i < DAYS_IN_A_WEEK) {
            System.out.println(sentimentTrend.get(i));
            i++;
        }
    }

    @Test
    public void getTotalPostingCount() {
        System.out.println(postingRepository.getTotalHotSpots());
    }

    @Test
    public void getCommentsInSenti() {
        Assert.isTrue(sentiRepository.getCommentsInSenti("代码").getBad() != 0);
        System.out.println(sentiRepository.getCommentsInSenti("京东"));
        System.out.println(sentiRepository.getCommentsInSenti(""));
        System.out.println(sentiRepository.getCommentsInSenti(null));
    }

}
