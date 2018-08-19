package com.belfry.bequank.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sf.json.JSONArray;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 6:00 PM 8/15/18
 * @Modifiedby:
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tutorial implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Long userid;        //准备换成User对象
    private int likecount;
    private String nickname,title,discription,content,time;
    private JSONArray keywords;
    @OneToMany(targetEntity = Comment.class, cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Comment> comments;
}
