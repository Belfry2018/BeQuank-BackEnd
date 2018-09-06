package com.belfry.bequank.controller;

import com.belfry.bequank.service.SystemUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 6:09 PM 8/15/18
 * @Modifiedby:
 */
@RestController
public class SystemUserController {
    @Autowired
    SystemUserService systemUserService;
    @PostMapping("/tutorial")
    public JSONObject postTutorial(HttpServletRequest request,@RequestBody JSONObject jsonObject){
        return systemUserService.postTutorial(request,
                    jsonObject.getString("author"),
                    jsonObject.getString("title"),
                    jsonObject.getString("cover"),
                    jsonObject.getString("abstract"),
                    jsonObject.getJSONArray("keyWords"),
                    jsonObject.getString("content"),
                    jsonObject.getString("time"),
                    jsonObject.getString("tutorialType"));

    }
}
