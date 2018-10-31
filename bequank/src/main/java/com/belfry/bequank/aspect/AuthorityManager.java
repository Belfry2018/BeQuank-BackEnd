package com.belfry.bequank.aspect;

import com.belfry.bequank.exception.AuthorityException;
import com.belfry.bequank.exception.TokenException;
import com.belfry.bequank.util.JwtUtil;
import com.belfry.bequank.util.Role;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.engine.transaction.jta.platform.internal.BorlandEnterpriseServerJtaPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
public class AuthorityManager {

    @Autowired
    JwtUtil jwtUtil;

    //普通用户专有方法权限验证
//    @Around(value = "execution(* com.belfry.bequank.service.NormalUserService.*(..))&&args(request,..)")
//    public Object authorizeNormalUser(ProceedingJoinPoint point, HttpServletRequest request) throws Throwable {
//        String token = request.getHeader("Authorization");
//        String role = getRole(token);
//
//        Object res = null;
//        if (!role.equals(Role.NORMAL)&&!role.equals(Role.SYSTEM)) {
//            throw new AuthorityException();
//        }
//
//        res = point.proceed();
//        return res;
//    }

    //系统用户专有方法权限验证
    @Around(value = "execution(* com.belfry.bequank.service.SystemUserService.*(..))&&args(request,..)")
    public Object authorizeSystemUser(ProceedingJoinPoint point, HttpServletRequest request) throws Throwable {
        String token = request.getHeader("Authorization");
        String role = getRole(token);

        Object res = null;
        if (!role.equals(Role.SYSTEM)) {
            throw new AuthorityException();
        }

        res = point.proceed();
        return res;
    }

    private String getRole(String token) {
        Map<String, Object> map = null;
        try {
            map = jwtUtil.parseToken(token);
        } catch (Exception e) {
            throw new TokenException();
        }
        String role = ((String) map.get("role"));
        return role;
    }
}
