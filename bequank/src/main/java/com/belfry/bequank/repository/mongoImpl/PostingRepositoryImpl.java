package com.belfry.bequank.repository.mongoImpl;

import com.belfry.bequank.entity.mongo.Posting;
import com.belfry.bequank.repository.mongo.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class PostingRepositoryImpl implements PostingRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ArrayList<Posting> getHotSpots(int page, int count) {
        Query q = new Query();
        q.with(new Sort(Sort.Direction.DESC, "created_at"));
        q.skip((page - 1) * count).limit(count);
        return (ArrayList<Posting>) mongoTemplate.find(q, Posting.class);
    }

    @Override
    public long getTotalHotSpots() {
        Query q = new Query();
        return mongoTemplate.count(q, Posting.class);
    }
}