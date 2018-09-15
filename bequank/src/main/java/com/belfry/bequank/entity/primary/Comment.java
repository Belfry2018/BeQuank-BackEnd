package com.belfry.bequank.entity.primary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(targetEntity = User.class, cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private User writer;         // 这个也准备换成User对象
    private String content,time,nickname;
    private boolean alreadyLiked;

    @ManyToOne(targetEntity = Tutorial.class, cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    private Tutorial tutorial;

    @ManyToOne(targetEntity = Comment.class, cascade = {CascadeType.MERGE})
    private Comment replyTarget;
    @OneToMany(targetEntity = Comment.class, cascade = {CascadeType.MERGE,CascadeType.REMOVE},mappedBy = "replyTarget")
    private List<Comment> comments;
    private ArrayList<Long> likedusers;
    public Comment(){
        this.likedusers=new ArrayList<>();
        this.comments=new ArrayList<>();
    }
    public Comment(User writer, String content, String time, String nickname, int likecount, Tutorial tutorial, Comment replyTarget) {
        this.writer = writer;
        this.content = content;
        this.time = time;
        this.nickname = nickname;
        this.tutorial = tutorial;
        this.replyTarget = replyTarget;
        this.comments = new ArrayList<>();
        this.likedusers=new ArrayList<>();
    }
    public JSONObject subtoJSONObject(Long writerid){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("commentId",this.getId());
        jsonObject.put("tutorialId",this.getTutorial().getId());
        jsonObject.put("writer",this.getWriter());
        jsonObject.put("content",this.content);
        jsonObject.put("time",this.time);
        jsonObject.put("likeCount",this.getLikedusers().size());
        jsonObject.put("alreadyLike",this.getLikedusers()==null?false:(this.getLikedusers().indexOf(writerid)==-1?false:true));
    return jsonObject;
    }
    public JSONObject toJSONObject(Long writerid){
        if(this.getReplyTarget()==null){
            JSONObject jsonObject=this.subtoJSONObject(writerid);
            jsonObject.put("fatherComment","");
            JSONArray jsonArray=new JSONArray();
            for(Comment c:this.getComments()){
                JSONObject object=c.subtoJSONObject(writerid);
                jsonObject.put("fatherComment","");
                jsonObject.put("childrenComments","");
                jsonArray.add(object);
            }
            jsonObject.put("childrenComments",jsonArray);
            return jsonObject;
        }
        return new JSONObject();
    }
}

