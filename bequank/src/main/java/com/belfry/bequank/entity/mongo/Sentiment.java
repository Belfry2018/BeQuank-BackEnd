package com.belfry.bequank.entity.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

//author: andi

@Getter
@NoArgsConstructor
@ToString
public class Sentiment {
    private ObjectId _id = new ObjectId();
    @Field("word")
    private String text = "";
    private double senti;
    @Field("created_date")
    private String date = "";
    private int good;
    private int mid;
    private int bad;
}
