package com.belfry.bequank.controller;

import com.belfry.bequank.entity.User;
import com.belfry.bequank.service.BaseService;
import com.belfry.bequank.util.Message;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BaseController {

    @Value("${belfry.expiration_time}")
    long expiration_time;
    @Value("${belfry.token_prefix}")
    String token_prefix;

    @Autowired
    BaseService baseService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/hello")
    public String hello(){
        return expiration_time + token_prefix;
    }


    @PostMapping("/identify")
    public JSONObject sendVerificationCode(@RequestBody JSONObject object) {
        String email = object.getString("email");
        logger.info("email = {}",email);
        JSONObject object1 = new JSONObject();
        try {
            object1 = baseService.sendVerificationCode(email);
        } catch (Exception e) {
            e.printStackTrace();
            object1.put("status", Message.MSG_EMAIL_FAILED);
            object1.put("message", "发生验证码失败");
        } finally {
            return object1;
        }
    }

    @PostMapping("/register")
    public JSONObject register(@RequestBody JSONObject object) {
        return baseService.register(object);
    }

    @PostMapping("/login")
    public JSONObject login(@RequestBody User user) {
        return baseService.login(user);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody User user) {
        baseService.logout(user);
    }

    @GetMapping("/test1")
    public String protectedRequest(HttpServletRequest request) {
        return "this is protected request";
    }

    @GetMapping("/test2")
    public String unprotectedRequest() {
        return "this is unprotected request";
    }
}
