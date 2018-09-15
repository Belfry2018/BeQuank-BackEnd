package com.belfry.bequank.repository.mongoImpl;

import com.belfry.bequank.entity.mongo.Posting;
import com.belfry.bequank.repository.mongo.PostingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Repository
public class PostingRepositoryImpl implements PostingRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ArrayList<Posting> getHotSpots(int page, int count) {
        Query q = new Query();
//      q.with(new Sort(Sort.Direction.DESC, "created_at"));
//        q.addCriteria(Criteria.where("text").ne("").ne(null));
        q.with(new Sort(Sort.Direction.DESC, "attitudes_count", "comments_count", "reposts_count"));
        q.skip((page - 1) * count).limit(count);
        ArrayList<Posting> res = (ArrayList<Posting>) mongoTemplate.find(q, Posting.class);
        //add avatar
//        for (int i = 0; i < res.size(); i++) {
//            Posting posting = res.get(i);
//            String name = posting.getUser();
//            long id = Long.parseLong(name);
//            Query query = new Query();
//            query.addCriteria(Criteria.where("id").is(id));
//            Users user = mongoTemplate.findOne(query, Users.class);
//            String avatar = user.getAvatar();
//            posting.setAvatar(avatar);
//        }
        return res;
    }

    @Override
    public long getTotalHotSpots() {
        Query q = new Query();
        return mongoTemplate.count(q, Posting.class);
    }
}