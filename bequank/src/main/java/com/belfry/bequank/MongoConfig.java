package com.belfry.bequank;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//author : andi

@Configuration
@EnableMongoRepositories("com.belfry.bequank.repository.mongo")
public class MongoConfig {

}
