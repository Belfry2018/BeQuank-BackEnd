package com.belfry.bequank.service;

import com.belfry.bequank.entity.User;
import net.sf.json.JSONObject;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;

public interface BaseService {
    public JSONObject register(JSONObject object);

    public JSONObject login(User user);

    public void logout(User user);

    public JSONObject sendVerificationCode(String email) throws GeneralSecurityException, MessagingException;

    public JSONObject getProfile(User user);

    public JSONObject setProfile(User user, JSONObject object);
}
