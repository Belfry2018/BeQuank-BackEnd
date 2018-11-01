package com.belfry.bequank.service;

import com.belfry.bequank.entity.primary.Tutorial;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户的共同api
 */
public interface UserService {
    JSONArray filterTutorials(String keyword, String tutorialType);

    JSONObject getTutorial(Long userid, Long id);

    JSONObject postComment(Long writerid, Long tutorialid, String content, String time);

    JSONObject reply(Long commenterid, Long commentid, String content, String time);

    JSONObject likeTutorial(Long likerid, Long tutorialid);

    JSONObject likeComment(Long likeerid, Long commentid);

    JSONArray recommendation(long userId);

    JSONObject getUnreadMessage(Long userid);

    JSONObject readMessage(Long userid,Long responseid);

    JSONArray getDalaos();

}
