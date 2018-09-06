package com.belfry.bequank.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

//author: andi

@Document(collection = "good_words")
public class GoodSentiment extends Sentiment {
}
