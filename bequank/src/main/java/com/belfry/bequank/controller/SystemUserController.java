package com.belfry.bequank.controller;

import com.belfry.bequank.entity.primary.User;
import com.belfry.bequank.service.SystemUserService;
import com.belfry.bequank.util.JwtUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 6:09 PM 8/15/18
 * @Modifiedby:
 */
@RestController
@RequestMapping("/api/v1")
public class SystemUserController {
    @Autowired
    SystemUserService systemUserService;
    @Autowired
    JwtUtil jwtUtil;
    @PostMapping("/tutorial")
    public JSONObject postTutorial(HttpServletRequest request,@RequestBody JSONObject jsonObject) {
        return systemUserService.postTutorial(request,
//                jsonObject.getString("author"),
                Long.parseLong(jwtUtil.parseToken(request.getHeader("Authorization")).get("userId").toString()),
                jsonObject.getString("title"),
                jsonObject.getString("cover"),
                jsonObject.getString("abstract"),
                null,
                jsonObject.getString("content"),
                jsonObject.getString("time"),
                jsonObject.getString("tutorialType"));

    }
}
