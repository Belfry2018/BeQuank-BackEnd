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
    @Pointcut("execution(* com.belfry.bequank.service.NormalUserService.*(request,..))&&args(request)")
    public void systemUser(HttpServletRequest request){}

    //系统用户api的切点
    @Pointcut("execution(* com.belfry.bequank.service.SystemUserService.*(request,..))&&args(request)")
    public void normalUser(HttpServletRequest request){}

    @Before("normalUser(request)")
    public void authorizeNormalUser(HttpServletRequest request) {

    }

    @Before("systemUser(request)")
    public void authorizeSystemUser(HttpServletRequest request) {

    }
}
