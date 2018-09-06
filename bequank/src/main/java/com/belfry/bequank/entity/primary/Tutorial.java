package com.belfry.bequank.entity.primary;

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
    private String nickname,title,description,content,time;
    private JSONArray keywords;
    @OneToMany(targetEntity = Comment.class, cascade = {CascadeType.MERGE,CascadeType.REMOVE}, fetch = FetchType.EAGER,mappedBy = "tutorial")
    private List<Comment> comments;

    public Tutorial(Long userid, int likecount, String nickname, String title, String description, String content, String time, JSONArray keywords) {
        this.userid = userid;
        this.likecount = likecount;
        this.nickname = nickname;
        this.title = title;
        this.description = description;
        this.content = content;
        this.time = time;
        this.keywords = keywords;
        this.comments = new ArrayList<>();
    }
}
