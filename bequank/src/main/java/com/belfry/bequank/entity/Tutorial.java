package com.belfry.bequank.entity;

import com.belfry.bequank.entity.primary.Comment;
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
    private Long userid;
    private int likecount;
    private String nickname,title,discription,content,time,cover,type;
    private JSONArray keywords;
    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments;
}
