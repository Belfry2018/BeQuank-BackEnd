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
    private ObjectId _id;
    @Field("word")
    private String text;
    private double senti;
    @Field("created_date")
    private String date;
}
