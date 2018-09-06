package com.belfry.bequank.entity.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

//author: andi

@Document(collection = "bad_words")
@Getter
@NoArgsConstructor
@ToString
public class BadSentiment extends Sentiment {
}
