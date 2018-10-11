package com.belfry.bequank.repository.mongoImpl;

import com.belfry.bequank.entity.mongo.*;
import com.belfry.bequank.repository.mongo.SentiRepository;
import com.belfry.bequank.util.DateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.*;

@Repository
public class SentiRepositoryImpl implements SentiRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    private final String TEXT_IN_DB = "原油";

    @Override
    public HashMap<String, Integer> getKeywords() {
        HashMap<String, Integer> keyWords = new HashMap<>();
//        ArrayList<Sentiment> goodSentiments = getSentiment(1);
//        ArrayList<Sentiment> badSentiments = getSentiment(0);
//        for (Sentiment sentiment : goodSentiments)
//            generateMap(keyWords, sentiment);
//        for (Sentiment sentiment : badSentiments)
//            generateMap(keyWords, sentiment);

//        List<Word_tf> words = mongoTemplate.find(new Query(),Word_tf.class);
//        for(Word_tf word:  words)
//            generateMap(keyWords,word);

        List<Word_tfidf> words = mongoTemplate.find(new Query(),Word_tfidf.class);
        for(Word_tfidf word:  words)
            generateMap(keyWords,word);

        return keyWords;
    }

    private void generateMap(HashMap<String,Integer> keyWords, Word_tf tf){
        String text = tf.getWord();
        int senti = (int)(tf.getTf()*100000);
        keyWords.put(text,senti);
    }

    private void generateMap(HashMap<String,Integer> keyWords, Word_tfidf tf){
        String text = tf.getWord();
        int senti = (int)(tf.getTfidf()*100000);
        keyWords.put(text,senti);
    }

    private void generateMap(HashMap<String, Integer> keyWords, Sentiment sentiment) {
        String text = sentiment.getText();
        int senti = (int) sentiment.getSenti();
        keyWords.put(text, senti);
    }

    private Query getQueryOfAWordForAWeek(String text) {
        Query q = new Query();
//        final int DAYS_NOT_IN_A_WEEK = 1;
//        q.skip(DAYS_NOT_IN_A_WEEK);
        List<String> datesInAWeek = DateHandler.dateToWeek();
        List<Criteria> criterias = new ArrayList<>();
        Criteria criteria = Criteria.where("word").is(text).and("created_date").in(datesInAWeek);
        q.addCriteria(criteria);
//        for(int i = 0; i <  7 ; i++){
//            System.out.println(datesInAWeek.get(i));
//            Criteria criteria = Criteria.where("word").is(text).and("created_date").is(datesInAWeek.get(i));
////            Criteria criteria = Criteria.where("word").is(text).and("created_date").in(datesInAWeek);
//            criterias.add(criteria);
//            System.out.println("Criteria"+criteria.getCriteriaObject());
//        }
////
//       q.addCriteria(new Criteria().orOperator(criterias.get(2), criterias.get(3)));
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

    private List noDup(List sentiments) {
        HashMap<String, Sentiment> map = new LinkedHashMap<>();
        for (int i = 0; i < sentiments.size(); i++) {
            Sentiment sentiment = (Sentiment) sentiments.get(i);
            map.put(sentiment.getDate(), sentiment);
        }
        sentiments = new ArrayList<Sentiment>();
        Collection<Sentiment> collection = map.values();
        sentiments.addAll(collection);
        return sentiments;
    }

    @Override
    public ArrayList<Sentiment> getSentimentTrend(String text) {
        if (text == null || text.length() == 0)
            text = TEXT_IN_DB;
        Query q = getQueryOfAWordForAWeek(text);
        ArrayList<Sentiment> sentiments = (ArrayList<Sentiment>)findInSentiment(q);
        ArrayList<Sentiment> res = new ArrayList<>();
        for (Sentiment sentiment:sentiments
             ) {
            System.out.println(sentiment);
        }
        for(String date : DateHandler.dateToWeek())
        {
            boolean hasSenti = false;
           for(int i = 0 ; i < sentiments.size() ; i++){
               Sentiment sentiment = sentiments.get(i);
               System.out.println("thisdate"+sentiment.getDate());
               if (sentiment.getDate().equals(date)) {
                   hasSenti = true;
                   res.add(sentiment);
                   break;
               }
           }
            if(!hasSenti)
            {
                res.add(new Sentiment());
            }
        }
        return res;
    }

    private List<Sentiment> findInSentiment(Query q) {
        List<GoodSentiment> goodSentiments = mongoTemplate.find(q, GoodSentiment.class);
        List<BadSentiment> badSentiments = mongoTemplate.find(q, BadSentiment.class);
        List sentiments = new ArrayList();
//        sentiments = mongoTemplate.find(q, GoodSentiment.class);
//        if (sentiments.isEmpty()) {
//            sentiments = mongoTemplate.find(q, BadSentiment.class);
//        }
        if( !goodSentiments.isEmpty())
            sentiments.addAll(goodSentiments);
        if(!badSentiments.isEmpty())
            sentiments.addAll(badSentiments);
        sentiments = noDup(sentiments);
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
        Sentiment sentiment;
        if(sentiments.size() == 2){
            if(DateHandler.formerEarlier(sentiments.get(0).getDate(), sentiments.get(1).getDate()))
                sentiment = sentiments.get(1);
            else
                sentiment = sentiments.get(0);
        }else{
            sentiment = sentiments.get(0);
        }
        System.out.println(sentiment);
        int good = sentiment.getGood();
        int mid = sentiment.getMid();
        int bad = sentiment.getBad();
        return new CommentsInSenti(good, mid, bad);
    }
}
