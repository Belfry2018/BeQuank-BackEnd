package com.belfry.bequank.entity.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document
public class UserBond {
    private long id;
    private String name;
}
