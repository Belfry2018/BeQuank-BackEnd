package com.belfry.bequank.service;

import com.belfry.bequank.entity.primary.Tutorial;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户的共同api
 */
public interface UserService {
    JSONArray filterTutorials(Long userid,String time,String title,String discription,String[] keywords);
    Tutorial getTutorial(Long id);
    JSONObject postComment(Long tutorialid,String content,String nickname,Long writerid,String time);
    JSONObject reply(Long commentid,String content,String nickname,Long writerid,String time);
    JSONObject likeTutorial(Long tutorialid,Long likerid);
    JSONObject likeComment(Long commentid,Long likerid);
}
