package com.belfry.bequank.repository.mongoImpl;

import com.belfry.bequank.entity.mongo.Posting;
import com.belfry.bequank.entity.mongo.Users;
import com.belfry.bequank.repository.mongo.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
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
//      q.with(new Sort(Sort.Direction.DESC, "created_at"));
        q.with(new Sort(Sort.Direction.DESC, "attitudes_count", "comments_count", "reposts_count"));
        q.skip((page - 1) * count).limit(count);
        ArrayList<Posting> res = (ArrayList<Posting>) mongoTemplate.find(q, Posting.class);
        //add avatar
        for (int i = 0; i < res.size(); i++) {
            Posting posting = res.get(i);
            String name = posting.getUser();
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is(name));
            Users user = mongoTemplate.findOne(query, Users.class);
            String avatar = user.getAvatar();
            posting.setAvatar(avatar);
        }
        return res;
    }

    @Override
    public long getTotalHotSpots() {
        Query q = new Query();
        return mongoTemplate.count(q, Posting.class);
    }
}