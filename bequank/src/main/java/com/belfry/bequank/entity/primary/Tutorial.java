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
public class Tutorial implements Serializable ,Comparable<Tutorial>{
    @Id
    @GeneratedValue
    private Long id;
    private Long userid;        
    private String nickname,title,description, time;
    @Column(length = 65536)
    private String content;
    private JSONArray keywords;
    private String type;
    private String cover;//封面
    private boolean alreadyLiked;
    @OneToMany(targetEntity = Comment.class, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER,mappedBy = "tutorial")
    private List<Comment> comments=new ArrayList<>();
    private ArrayList<Long> likedlist=new ArrayList<>();
    
    public Tutorial(Long userid, String nickname, String title, String description, String content, String time, JSONArray keywords) {
        this.userid = userid;
        this.nickname = nickname;
        this.title = title;
        this.description = description;
        this.content = content;
        this.time = time;
        this.keywords = keywords;
    }

    @Override
    public int compareTo(Tutorial o) {
        Integer thiscount=new Integer(this.getLikedlist().size());
        Integer thatcount=new Integer(o.getLikedlist().size());
        return thiscount.compareTo(thatcount);
    }
}
