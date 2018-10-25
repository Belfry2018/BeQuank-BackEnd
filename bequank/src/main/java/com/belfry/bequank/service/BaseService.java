package com.belfry.bequank.service;

import com.belfry.bequank.entity.primary.User;
import net.sf.json.JSONObject;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;

public interface BaseService {
    public JSONObject register(JSONObject object);

    public JSONObject login(User user);

    public void logout(User user);

    public JSONObject sendVerificationCode(String email) throws GeneralSecurityException, MessagingException;

    public JSONObject getProfile(long userId);

    public JSONObject setProfile(long userId, JSONObject user);

    public JSONObject setPassword(long userId, JSONObject object);

}
