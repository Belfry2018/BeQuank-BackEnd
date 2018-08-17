package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.Tutorial;
import com.belfry.bequank.repository.TutorialRepository;
import com.belfry.bequank.repository.UserRepository;
import com.belfry.bequank.service.SystemUserService;
import com.belfry.bequank.util.Message;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 5:59 PM 8/15/18
 * @Modifiedby:
 */
@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    TutorialRepository tutorialRepository;
    @Autowired
    UserRepository userRepository;
    Message message=new Message();

    @Override
    public JSONObject postTutorial(String nickname, Long userid, String title, String discription, JSONArray keywords, String content, String time) {
        /**
         * @author: Yang Yuqing
         * @description:
         * @param nickname
        * @param userid
        * @param title
        * @param discription
         * @description: keyword is a JSONArray: we back-ends don't care a shit about what is actually is!
         *               It's our dear front-end fellows' business!
         *               * Now I begin being confused how the HELL the keyword works!
        * @param content
        * @param time
         * @return net.sf.json.JSONObject
         * @date: 6:17 PM 8/15/18
         */
        JSONObject jsonObject=new JSONObject();
        Tutorial tutorial=new Tutorial();
        tutorial.setNickname(nickname);
        tutorial.setUserid(userid);
        tutorial.setTitle(title);
        tutorial.setTime(time);
        tutorial.setDiscription(discription);
        tutorial.setKeywords(keywords);
        tutorial.setContent(content);
        if(userRepository.findById(userid)==null)jsonObject.put("code",Message.MSG_USER_NOTEXIST);
        else {
            tutorialRepository.save(tutorial);
            jsonObject.put("code",Message.MSG_SUCCESS);
        }
        return jsonObject;
    }

}
