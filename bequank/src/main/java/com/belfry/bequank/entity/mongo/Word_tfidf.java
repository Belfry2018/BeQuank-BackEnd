package com.belfry.bequank.entity.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@NoArgsConstructor
@ToString
public class Word_tfidf {
    private ObjectId _id = new ObjectId();
    private String word = "";
    private double tfidf = 0;
    private String created_date = "";
}
