package com.belfry.bequank.entity.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
@Document
public class Users {
    private ObjectId _id = new ObjectId();
    @Field("id")
    private long users_id;
    private String name;
    private String avatar;
    private String cover;
    private String gender;
    private String description;
    private int fans_count;
    private int follows_count;
    private int weibos_count;
    private boolean verified;
    private String verified_reason;
    private int verified_type;
    private String crawled_at;
    private List<UserBond> follows;
    private List<UserBond> fans;
}
