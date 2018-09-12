package com.belfry.bequank.controller;

import com.belfry.bequank.entity.primary.User;
import com.belfry.bequank.service.BaseService;
import com.belfry.bequank.util.JwtUtil;
import com.belfry.bequank.util.Message;
import com.belfry.bequank.util.OSSHandler;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class BaseController {

    @Value("${belfry.expiration_time}")
    long expiration_time;
    @Value("${belfry.token_prefix}")
    String token_prefix;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BaseService baseService;

    @Autowired
    OSSHandler ossHandler;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/hello")
    public String hello() {
        return expiration_time + token_prefix;
    }


    @PostMapping("/identify")
    public JSONObject sendVerificationCode(@RequestBody JSONObject object) {
        String email = object.getString("email");
        logger.info("email = {}", email);
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

    @GetMapping("/user/profile")
    public User getProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Map<String, Object> map = jwtUtil.parseToken(token);
        long userId = Integer.toUnsignedLong((int)map.get("userId"));
        System.out.println("ss "+baseService.getProfile(userId).getUserName());
        return baseService.getProfile(userId);
    }

    @PostMapping("/user/profile")
    public JSONObject setProfile(HttpServletRequest request, @RequestBody User user) {
        String token = request.getHeader("Authorization");
        Map<String, Object> map = jwtUtil.parseToken(token);
        long userId = Integer.toUnsignedLong((int) map.get("userId"));
        System.out.println("sdf");
        return baseService.setProfile(userId, user);
    }

    @PostMapping("/user/password")
    public JSONObject setPassword(HttpServletRequest request, @RequestBody JSONObject object){
        String token = request.getHeader("Authorization");
        Map<String, Object> map = jwtUtil.parseToken(token);
        long userId = Integer.toUnsignedLong((int) map.get("userId"));
        return baseService.setPassword(userId, object);
    }

    @PostMapping("/user/avatar")
    public JSONObject setAvatar(HttpServletRequest request) throws IOException {

        long time = System.currentTimeMillis();
        MultipartFile file = ((MultipartRequest) request).getFile("avatar");
//        logger.info("file = {}", file);

        String path = time + file.getOriginalFilename().replaceAll(" ", "");
        File temp = new File(path);
        BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream());
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(temp));

        byte[] bf = new byte[2048];
        int len = 0;
        while ((len = inputStream.read(bf)) > 0) {
            outputStream.write(bf, 0, len);
        }
        outputStream.flush();
        inputStream.close();
        outputStream.close();

        JSONObject object = new JSONObject();
        String url = ossHandler.upload(temp);
        temp.delete();
        object.put("url", url);
        object.put("status", url == null ? Message.MSG_FAILED : Message.MSG_SUCCESS);

        return object;
    }
}
