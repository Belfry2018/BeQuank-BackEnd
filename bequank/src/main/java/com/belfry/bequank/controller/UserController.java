package com.belfry.bequank.controller;

import com.belfry.bequank.entity.primary.RealStock;
import com.belfry.bequank.entity.primary.Strategy;
import com.belfry.bequank.entity.primary.Tutorial;
import com.belfry.bequank.service.NormalUserService;
import com.belfry.bequank.service.UserService;
import com.belfry.bequank.util.JwtUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    JwtUtil util;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    // TODO: 9/7/18 将一些map url前加上/api/v1以与前端对应
    // TODO: 9/7/18 要处理所有jsonobject可能为null的问题。
    @PostMapping("/tutorials")
    public JSONArray filterTutorials(@RequestBody JSONObject jsonObject){
        System.out.println("the request is"+jsonObject);
        return userService.filterTutorials(
                jsonObject.getString("keywords"),
                jsonObject.getString("tutorialType"));
    }

    @GetMapping("/tutorial")
    public JSONObject getTutorial(HttpServletRequest request,@RequestParam String id){
        System.out.println("id is "+id);
        if(request.getHeader("Authorization")==null||request.getHeader("Authorization").equals("null"))
            return userService.getTutorial((long)0,Long.parseLong(id));
        return userService.getTutorial(
                Long.parseLong(util.parseToken(request.getHeader("Authorization")).get("userId").toString()),
                Long.parseLong(id));
    }

    @PostMapping("/comment")
    public JSONObject postComment(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.postComment(
                Long.parseLong(util.parseToken(request.getHeader("Authorization")).get("userId").toString()),
                jsonObject.getLong("tutorialId"),
                jsonObject.getString("content"),
                jsonObject.getString("time")
        );
    }

    @PostMapping("/reply")
    public JSONObject reply(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject);
        return userService.reply(
                Long.parseLong(util.parseToken(request.getHeader("Authorization")).get("userId").toString()),
                jsonObject.getLong("commentId"),
                jsonObject.getString("content"),
                jsonObject.getString("time")
        );
    }

    @PostMapping("/like/tutorial")
    public JSONObject likeTutorial(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.likeTutorial(
                Long.parseLong(util.parseToken(request.getHeader("Authorization")).get("userId").toString()),
                jsonObject.getLong("tutorialId")
        );
    }

    @PostMapping("/like/comment")
    public JSONObject likeComment(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return userService.likeComment(
                Long.parseLong(util.parseToken(request.getHeader("Authorization")).get("userId").toString()),
                jsonObject.getLong("commentId")
        );
    }

    /**
     * 计算问卷结果
     *
     * @param request
     * @return
     */
    @PostMapping("/strategy/answer")
    public JSONObject getQuestionnairResult(HttpServletRequest request, @RequestBody JSONObject answer) {
        return normalUserService.getQuestionnairResult(request, answer);
    }

    /**
     * 获得推荐策略
     * @param request
     * @return
     */
    @GetMapping("/strategy/recommend/profit")
    public String recommendStockByProfit(HttpServletRequest request) throws IOException {
        return normalUserService.recommendByProfit(request);
    }

    @GetMapping("/strategy/recommend/risk")
    public String recommendStockByRisk(HttpServletRequest request) throws IOException {
        return normalUserService.recommendByRisk(request);
    }

    /**
     * 查看单只股票的详细信息
     * @param request
     * @param stockId
     * @return
     */
    @GetMapping("/stock/{stockId}")
    public String viewAStock(HttpServletRequest request, @PathVariable String stockId) {

        try {
            return normalUserService.getAStock(request, stockId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查看所有股票的简略信息
     *
     * @param request
     * @return
     */
    @GetMapping("/stocks/{page}")
    public List<RealStock> viewStocks(HttpServletRequest request, @PathVariable int page) {
        return normalUserService.getStocks(request,page).stream().collect(Collectors.toList());
    }

    @PostMapping("/strategy/record")
    public JSONObject addStrategy(HttpServletRequest request, @RequestBody JSONObject strategy) {
        return normalUserService.addStrategy(request, strategy);
    }

    @DeleteMapping("/strategy/record/{recordId}")
    public void deleteStrategy(HttpServletRequest request, @PathVariable long recordId) {
        normalUserService.deleteStrategy(request, recordId);
    }

    @GetMapping("/strategy/records")
    public List<Strategy> getStrategies(HttpServletRequest request) {
        return normalUserService.getStrategies(request);
    }

    @GetMapping("/strategy/record/{recordId}")
    public String getAStrategy(HttpServletRequest request, @PathVariable long recordId) throws MalformedURLException {
        return normalUserService.getAStrategy(request, recordId);
    }

    @GetMapping("/tutorials/recommendation")
    public JSONArray recommendation(){
        return userService.recommendation();
    }

    @GetMapping("/user/message")
    public JSONObject getUnreadMessage(HttpServletRequest request){
        return userService.getUnreadMessage(
                Long.parseLong(util.parseToken(request.getHeader("Authorization")).get("userId").toString())
        );
    }
    @PostMapping("user/message")
    public void readMessage(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        userService.readMessage(
                Long.parseLong(util.parseToken(request.getHeader("Authorization")).get("userId").toString()),
                jsonObject.getLong("responseId")
        );

    }
    @GetMapping("user/dalaos")
    public JSONArray getDalaos(){
        return userService.getDalaos();
    }

}
