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
    private Long writerid;         // 这个也准备换成User对象
    private String content,time,nickname;
    private int likecount;

    @ManyToOne(targetEntity = Tutorial.class,cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Tutorial tutorial;

    @ManyToOne(targetEntity = Comment.class,cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Comment replyTarget;
    @OneToMany(targetEntity = Comment.class, cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Comment> comments;
}

