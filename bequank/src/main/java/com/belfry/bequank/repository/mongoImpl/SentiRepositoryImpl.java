package com.belfry.bequank.repository.mongoImpl;

import com.belfry.bequank.entity.mongo.BadSentiment;
import com.belfry.bequank.entity.mongo.CommentsInSenti;
import com.belfry.bequank.entity.mongo.GoodSentiment;
import com.belfry.bequank.entity.mongo.Sentiment;
import com.belfry.bequank.repository.mongo.SentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SentiRepositoryImpl implements SentiRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    private final String TEXT_IN_DB = "原油";

    @Override
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

    private Query getQueryOfAWordForAWeek(String text) {
        Query q = new Query();
        final int DAYS_NOT_IN_A_WEEK = 1;
        q.skip(DAYS_NOT_IN_A_WEEK);
        q.addCriteria(Criteria.where("word").is(text));
        return q;
    }

    private Query getQueryOfAWordForAll(String text) {
        Query q = new Query();
        q.addCriteria(Criteria.where("word").is(text));
        return q;
    }

    private Query getTheNewestOne(String text) {
        Query q = getQueryOfAWordForAll(text);
//        q.with(new Sort(Sort.Direction.DESC, "_id"));
        q.with(new Sort(Sort.Direction.DESC, "created_date"));
        final int RESULT_LIMIT = 1;
        q.limit(RESULT_LIMIT);
        return q;
    }

    @Override
    public ArrayList<Sentiment> getSentiment(int type) {
        final int GOOD = 1;
        final int BAD = 0;
        List sentiments;
        Query q = new Query();
        final int MAX_SENTIMENTS_A_DAY = 500;
        q.with(new Sort(Sort.Direction.DESC, "_id"));
        q.limit(MAX_SENTIMENTS_A_DAY);
        if (type == GOOD)
            sentiments = mongoTemplate.find(q, GoodSentiment.class);
        else
            sentiments = mongoTemplate.find(q, BadSentiment.class);
        return (ArrayList<Sentiment>) sentiments;
    }

    @Override
    public ArrayList<Sentiment> getSentimentTrend(String text) {
        if (text == null || text.length() == 0)
            text = TEXT_IN_DB;
        Query q = getQueryOfAWordForAWeek(text);
        List sentiments = findInSentiment(q);

        HashMap<String, Sentiment> map = new LinkedHashMap<>();
        for (int i = 0; i < sentiments.size(); i++) {
            Sentiment sentiment = (Sentiment) sentiments.get(i);
            map.put(sentiment.getDate(), sentiment);
        }
        sentiments = new ArrayList<Sentiment>();
        Collection<Sentiment> collection = map.values();
        sentiments.addAll(collection);

        if (sentiments.size() < 7) {
            q = getQueryOfAWordForAll(text);
            sentiments = findInSentiment(q);
            while (sentiments.size() < 7)
                sentiments.add(new Sentiment());
        }
        return (ArrayList<Sentiment>) (sentiments);
    }

    private List<Sentiment> findInSentiment(Query q) {
        List sentiments;
        sentiments = mongoTemplate.find(q, GoodSentiment.class);
        if (sentiments.isEmpty()) {
            sentiments = mongoTemplate.find(q, BadSentiment.class);
        }
        return sentiments;
    }

    @Override
    public CommentsInSenti getCommentsInSenti(String text) {
        if (text == null || text.length() == 0)
            text = TEXT_IN_DB;
        Query q = getTheNewestOne(text);
        List<Sentiment> sentiments = findInSentiment(q);
        if (sentiments.size() < 1)
            return getCommentsInSenti(TEXT_IN_DB);
        Sentiment sentiment = sentiments.get(0);
        System.out.println(sentiment);
        int good = sentiment.getGood();
        int mid = sentiment.getMid();
        int bad = sentiment.getBad();
        return new CommentsInSenti(good, mid, bad);
    }
}
