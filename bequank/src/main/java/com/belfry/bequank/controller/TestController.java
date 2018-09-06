package com.belfry.bequank.controller;

import com.belfry.bequank.entity.primary.Tutorial;
import com.belfry.bequank.entity.primary.User;
import com.belfry.bequank.repository.primary.CommentRepository;
import com.belfry.bequank.repository.primary.TutorialRepository;
import com.belfry.bequank.repository.primary.UserRepository;
import com.belfry.bequank.service.BaseService;
import com.belfry.bequank.service.SystemUserService;
import com.belfry.bequank.service.UserService;
import com.belfry.bequank.util.Message;
import com.belfry.bequank.util.Role;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 6:05 PM 8/17/18
 * @Modifiedby:
 */
@RestController
public class TestController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TutorialRepository tutorialRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserService userService;
    @Autowired
    SystemUserService systemUserService;
    @Autowired
    BaseService baseService;
    @GetMapping("f")
    JSONObject getOne(){
        User u=userRepository.getById((long)1);
        return JSONObject.fromObject(u);
    }
    @GetMapping("tregister")
    JSONObject testLogin(){
        User u=new User();
        u.setRole(Role.NORMAL);
        u.setNickname("hayaku");
        u.setPassword("666");
        u.setUserName("bequank@qq.com");
        return baseService.register(JSONObject.fromObject(u));
    }
//    @GetMapping("temail")
//    public JSONObject testEmail(){
//        String email="161250179@smail.nju.edu.cn";
//        JSONObject object1 = new JSONObject();
//        try {
//            object1 = baseService.sendVerificationCode(null,email);
//        } catch (Exception e) {
//            e.printStackTrace();
//            object1.put("status", Message.MSG_EMAIL_FAILED);
//            object1.put("message", "发生验证码失败");
//        } finally {
//            return object1;
//        }
//    }
//    @GetMapping("newTutorial")
//    public JSONObject newTutorial(){
//        String[] keywords={"bequank","first"};
//                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
//                JSONArray.fromObject(null)  ,"content","2018-08-24"
//                );
//    }
//    @GetMapping("tftutorials")
//    public JSONArray testTutorials(){
//        return userService.filterTutorials((long)1,null,null,null,null);
//    }

    @GetMapping("ttutorial")
    Tutorial getTutorial(){
        return userService.getTutorial((long)1);
    }
    @GetMapping("tcomment")
    JSONObject postComment(){
        return userService.postComment((long)1,"hahahahah","jjj",(long)1,"2018-8-17");
    }

    @GetMapping("treply")
    JSONObject reply(){
        return userService.postComment((long)1,"prprpr","jjj",(long)1,"2018-8-17");
    }
    @GetMapping("tlike/tutorial")
    JSONObject likeTutorial(){
        return userService.likeTutorial((long)1,(long)1);
    }
    @GetMapping("tlike/comment")
    JSONObject likeComment(){
        return userService.likeComment(
                (long)1,
                (long)1
        );
    }
}
