package com.belfry.bequank.aspect;

import com.belfry.bequank.exception.TokenException;
import com.belfry.bequank.util.JwtUtil;
import com.belfry.bequank.util.Message;
import io.jsonwebtoken.Jwts;
import net.sf.json.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.client.HttpStatusCodeException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.http.HTTPException;
import java.util.Map;

@Aspect
@Component
public class RequestFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PathMatcher matcher = new AntPathMatcher();

    @Autowired
    StringRedisTemplate template;
    @Autowired
    JwtUtil jwtUtil;

    @Value("${belfry.header_string}")
    String header;
    @Value("${belfry.token_prefix}")
    String token_prefix;
    @Value("${belfry.secret}")
    String secret;
    @Value("${belfry.key_prefix}")
    String key_prefix;


    @Around(value = "execution(* com.belfry.bequank.controller.*.*(..))&&args(request,..)")
    public Object validateRequest(ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest request) throws Throwable {
        String uri = request.getRequestURI();

        logger.info("protected request : {}", uri);
        String token = request.getHeader(header);

        JSONObject response = new JSONObject();
        if (token != null) {
            Map<String, Object> map = jwtUtil.parseToken(token);

            String userName = ((String) map.get("userName"));
            int time = ((int) map.get("exp"));
            String val = template.opsForValue().get(key_prefix + userName);

            if (val == null) {
                throw new TokenException();
            }
            if (!val.equals(token)) {
                logger.info("val = {}", val);
                logger.info("token = {}", token);
                throw new TokenException();
            }
            if (time < System.currentTimeMillis() / 1000) {    //÷1000是为了单位对齐……
                logger.info("expire time = {}", time);
                logger.info("current time = {}", System.currentTimeMillis() / 1000);
                throw new TokenException();
            }
        } else {
            throw new TokenException();

        }
        Object result = proceedingJoinPoint.proceed();
        return result;
    }


}
