package com.belfry.bequank.repository.mongoImpl;

import com.belfry.bequank.entity.mongo.BadSentiment;
import com.belfry.bequank.entity.mongo.GoodSentiment;
import com.belfry.bequank.entity.mongo.Sentiment;
import com.belfry.bequank.repository.mongo.SentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class SentiRepositoryImpl implements SentiRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public HashMap<String, Integer> getKeywords() {
        HashMap<String, Integer> keyWords = new HashMap<>();
        ArrayList<Sentiment> goodSentiments = getSentiment(1);
        ArrayList<Sentiment> badSentiments = getSentiment(0);
        for (Sentiment sentiment : goodSentiments)
            generateMap(keyWords, sentiment);
        for (Sentiment sentiment : badSentiments)
            generateMap(keyWords, sentiment);


        return keyWords;
    }

    private void generateMap(HashMap<String, Integer> keyWords, Sentiment sentiment) {
        String text = sentiment.getText();
        int senti = (int) sentiment.getSenti();
        keyWords.put(text, senti);

    }

    public ArrayList<Sentiment> getSentiment(int type) {
        final int GOOD = 1;
        final int BAD = 0;
        List sentiments = new ArrayList<Sentiment>();
        Query q = new Query();
        final int MAX_SENTIMENTS_A_DAY = 500;
        q.with(new Sort(Sort.Direction.DESC, "_id"));
        q.limit(MAX_SENTIMENTS_A_DAY);
        if (type == GOOD)
            sentiments = mongoTemplate.find(q, GoodSentiment.class);
        else if (type == BAD)
            sentiments = mongoTemplate.find(q, BadSentiment.class);
        return (ArrayList<Sentiment>) sentiments;
    }

    public ArrayList<Sentiment> getSentimentTrend(String text) {
        Query q = new Query();
        final int DAYS_NOT_IN_A_WEEK = 1;
        q.skip(DAYS_NOT_IN_A_WEEK);
        q.addCriteria(Criteria.where("word").is(text));
        List sentiments;
        sentiments = mongoTemplate.find(q, GoodSentiment.class);
        if (sentiments.isEmpty()) {
            sentiments = mongoTemplate.find(q, BadSentiment.class);
        }
        return (ArrayList<Sentiment>) (sentiments);

    }

}
