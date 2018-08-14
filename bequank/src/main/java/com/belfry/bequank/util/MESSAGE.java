package com.belfry.bequank.util;

public class Message {
    public static final int MSG_SUCCESS         = 200 ;

    public static final int MSG_EMAIL_FAILED  = 417 ;   //发送验证码失败
    public static final int MSG_WRONG_VERICODE  = 418 ; //验证码错误
    public static final int MSG_DUPLICATE_EMAIL = 419 ; //用户名重复

    public static final int MSG_WRONG_PASSWORD  = 420 ; //密码错误
    public static final int MSG_USER_NOTEXIST   = 421 ; //用户不存在
}
