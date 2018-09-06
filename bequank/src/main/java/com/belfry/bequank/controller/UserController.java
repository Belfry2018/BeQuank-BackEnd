package com.belfry.bequank.controller;

import com.belfry.bequank.entity.Tutorial;
import com.belfry.bequank.repository.TutorialRepository;
import com.belfry.bequank.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 9:13 PM 8/16/18
 * @Modifiedby:
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/tutorials")
    public JSONArray filterTutorials(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        return userService.filterTutorials(
                jsonObject.getLong("authorId"),
                jsonObject.getString("publishTime"),
                jsonObject.getString("cover"),
                jsonObject.getString("abstract"),
                null,//discription deleted from API
                jsonObject.getString("keyWords").split(" "),
                jsonObject.getString("tutorialType"));
    }
    @GetMapping("/tutorial")
    Tutorial getTutorial(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.getTutorial(jsonObject.getLong("id"));
    }
    @PostMapping("/comment")
    JSONObject postComment(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.postComment(
                jsonObject.getLong("tutorialid"),
                jsonObject.getString("content"),
                jsonObject.getString("nickname"),
                jsonObject.getLong("writerid"),
                jsonObject.getString("time")
        );
    }

    @PostMapping("/reply")
    JSONObject reply(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.postComment(
                jsonObject.getLong("commentid"),
                jsonObject.getString("content"),
                jsonObject.getString("nickname"),
                jsonObject.getLong("writerid"),
                jsonObject.getString("time")
        );
    }
    @PostMapping("/like/tutorial")
    JSONObject likeTutorial(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.likeTutorial(
                jsonObject.getLong("tutorialid"),
                jsonObject.getLong("likerid")
        );
    }
    @PostMapping("/like/comment")
    JSONObject likeComment(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.likeComment(
                jsonObject.getLong("commentid"),
                jsonObject.getLong("likerid")
        );
    }
}
