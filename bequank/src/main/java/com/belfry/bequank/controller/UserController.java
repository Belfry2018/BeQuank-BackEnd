package com.belfry.bequank.controller;

import com.belfry.bequank.entity.primary.Strategy;
import com.belfry.bequank.entity.primary.Tutorial;
import com.belfry.bequank.service.NormalUserService;
import com.belfry.bequank.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 9:13 PM 8/16/18
 * @Modifiedby:
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    NormalUserService normalUserService;

    @PostMapping("/tutorials")
    public JSONArray filterTutorials(HttpServletRequest request, @RequestBody JSONObject jsonObject){
        return userService.filterTutorials(
                jsonObject.getLong("userid"),
                jsonObject.getString("time"),
                jsonObject.getString("title"),
                jsonObject.getString("description"),
                jsonObject.getString("keywords").split(" "));
    }

    @GetMapping("/tutorial")
    public Tutorial getTutorial(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.getTutorial(jsonObject.getLong("id"));
    }

    @PostMapping("/comment")
    public JSONObject postComment(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.postComment(
                jsonObject.getLong("tutorialid"),
                jsonObject.getString("content"),
                jsonObject.getString("nickname"),
                jsonObject.getLong("writerid"),
                jsonObject.getString("time")
        );
    }

    @PostMapping("/reply")
    public JSONObject reply(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.postComment(
                jsonObject.getLong("commentid"),
                jsonObject.getString("content"),
                jsonObject.getString("nickname"),
                jsonObject.getLong("writerid"),
                jsonObject.getString("time")
        );
    }

    @PostMapping("/like/tutorial")
    public JSONObject likeTutorial(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.likeTutorial(
                jsonObject.getLong("tutorialid"),
                jsonObject.getLong("likerid")
        );
    }

    @PostMapping("/like/comment")
    public JSONObject likeComment(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.likeComment(
                jsonObject.getLong("commentid"),
                jsonObject.getLong("likerid")
        );
    }

    /**
     * 计算问卷结果
     *
     * @param request
     * @return
     */
    @PostMapping("/strategy/answer")
    public JSONObject getQuestionnairResult(HttpServletRequest request, JSONObject answer) {
        return normalUserService.getQuestionnairResult(request, answer);
    }

    /**
     * 获得推荐策略
     * @param request
     * @return
     */
    @GetMapping("/strategy/recommend")
    public JSONObject recommendStock(HttpServletRequest request) {
        return null;
    }

    /**
     * 查看单只股票的详细信息
     * @param request
     * @param stockId
     * @return
     */
    @GetMapping("/stock")
    public JSONObject viewAStock(HttpServletRequest request, @PathVariable String stockId) {
        return null;
    }

    /**
     * 查看所有股票的简略信息
     *
     * @param request
     * @return
     */
    @GetMapping("/stocks")
    public JSONObject viewStocks(HttpServletRequest request, @PathVariable int page) {
        return null;
    }

    @PostMapping("/strategy/record")
    public JSONObject addStrategy(HttpServletRequest request, Strategy strategy) {
        return normalUserService.addStrategy(request, strategy);
    }

    @DeleteMapping("/strategy/record")
    public void deleteStrategy(HttpServletRequest request, @PathVariable long recordId) {
        normalUserService.deleteStrategy(request, recordId);
    }

    @GetMapping("/strategy/records")
    public List<Strategy> getStrategies(HttpServletRequest request) {
        return normalUserService.getStrategies(request);
    }

    @GetMapping("/strategy/record")
    public Strategy getAStrategy(HttpServletRequest request, @PathVariable long recordId) {
        return normalUserService.getAStrategy(request, recordId);
    }
}
