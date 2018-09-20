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
public class Word_tf {
    private ObjectId _id = new ObjectId();
    private String word = "";

    private String created_date = "";
    private double tf = 0;
}
