package com.belfry.bequank.service;

import com.belfry.bequank.entity.primary.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * 系统用户api
 */
public interface SystemUserService {
    JSONObject postTutorial(HttpServletRequest request, Long author, String title, String cover, String discription, JSONArray keywords, String content, String time, String type);
}
