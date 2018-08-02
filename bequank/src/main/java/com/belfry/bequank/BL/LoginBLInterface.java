package com.belfry.bequank.BL;

import com.belfry.bequank.repository.UserRepository;
import com.belfry.bequank.util.LoginInfo;
import net.sf.json.JSONObject;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 8:54 AM 8/2/18
 * @Modifiedby:
 */

public interface LoginBLInterface {
    JSONObject login(String username, String password, Map<String, String> userlist);
    int sendVerificationCode(String email,Map<String,Integer> vericodelist) throws MessagingException, GeneralSecurityException ;
    public JSONObject signup(String email,String nickname,String password,int vericode,Map<String,Integer>vericodelist);

}
