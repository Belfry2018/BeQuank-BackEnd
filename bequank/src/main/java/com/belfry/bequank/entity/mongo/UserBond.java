package com.belfry.bequank.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserBond {
    private long id;
    private String name;
}
