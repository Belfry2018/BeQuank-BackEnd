package com.belfry.bequank.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 5:21 PM 8/17/18
 * @Modifiedby:
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Long tutorialid,writerid;
    private String content,time,nickname;
    private int likecount;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments;
}

