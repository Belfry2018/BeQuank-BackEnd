package com.belfry.bequank.entity.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

//author: andi

@Document(collection = "weibos")
@Getter
@NoArgsConstructor
@ToString
public class Posting implements Serializable {
    private ObjectId _id;
    private String user;// 发布者
    private int attitudes_count;
    private int comments_count;
    private int reposts_count;
    private String text;
    @Field("created_at")
    private String time;// 2018-8-23 22:38

    @Field("id")
    private String text_id;
    private String crawled_at;
    private String source;
    private String created_date;
    private double senti;
    private long level;
    private String full_text;
    private List<String> words;


}
