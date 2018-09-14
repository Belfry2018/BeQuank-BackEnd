package com.belfry.bequank.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CommentsInSenti {
    private int good;
    private int mid;
    private int bad;
}
