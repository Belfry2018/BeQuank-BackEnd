package com.belfry.bequank.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthorityManager {

    //普通用户api的切点
    @Pointcut("execution(* com.belfry.bequank.service.NormalUserService.*())&&args(request,..)")
    public void systemUser(HttpServletRequest request){}

    //系统用户api的切点
    @Pointcut("execution(* com.belfry.bequank.service.SystemUserService.*())&&args(request,..)")
    public void normalUser(HttpServletRequest request){}

    @Before(value = "normalUser(request)",argNames = "request")
    public void authorizeNormalUser(HttpServletRequest request) {

    }

    @Before(value = "systemUser(request)",argNames = "request")
    public void authorizeSystemUser(HttpServletRequest request) {

    }
}
