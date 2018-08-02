package com.belfry.bequank.Controller;

import com.belfry.bequank.BL.LoginBL;
import com.belfry.bequank.repository.UserRepository;
import com.belfry.bequank.util.MESSAGE;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 11:47 AM 8/1/18
 * @Modifiedby:
 */
@Controller

public class UserController {
    Map<Long,String> userlist=new HashMap<Long , String>();
    LoginBL loginBL=new LoginBL();
    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value="/register",method=RequestMethod.GET)
    @ResponseBody
    public JSONObject register(@RequestBody JSONObject jsonObject){
        return loginBL.signup(jsonObject.getString("email"),
                jsonObject.getString("nickname"),
                jsonObject.getString("password"),
                userRepository);

    }
    @RequestMapping(value="/login",method=RequestMethod.GET)
    @ResponseBody
    public JSONObject login(@RequestBody JSONObject jsonObject){
        System.out.println("invoked login");
        return loginBL.login(jsonObject.getString("username"),
                jsonObject.getString("password"),
                userRepository,
                userlist);
    }
    @RequestMapping(value="/identify",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject identify(@RequestBody JSONObject jsonObject){
        JSONObject response=new JSONObject();
        try{
            response.put("code",loginBL.sendVerificationCode(jsonObject.getString("email")));
            response.put("status",MESSAGE.MSG_SUCCESS);
            return response;
        }catch(Exception e){
            response.put("code",0);
            response.put("status",MESSAGE.MSG_EMAIL_FAILED);
            e.printStackTrace();
            return response;
        }
    }
}
