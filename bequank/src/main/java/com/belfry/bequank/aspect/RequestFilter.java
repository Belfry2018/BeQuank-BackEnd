package com.belfry.bequank.aspect;

import io.jsonwebtoken.Jwts;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
public class RequestFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PathMatcher matcher = new AntPathMatcher();

    @Autowired
    StringRedisTemplate template;

    @Value("${belfry.header_string}")
    String header;
    @Value("${belfry.token_prefix}")
    String token_prefix;
    @Value("${belfry.secret}")
    String secret;
    @Value("${belfry.key_prefix}")
    String key_prefix;

    @Pointcut("execution(* com.belfry.bequank.controller.*.*(..))&&args(request,..)")
    public void invokeMethod(HttpServletRequest request) {}

    @Before(value = "invokeMethod(request)", argNames = "request")
    public void validateRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (matcher.match("/api/**", request.getServletPath())) {
            //protected request
            logger.info("protected request : {}", uri);
            String token = request.getHeader(header);
            if (token != null) {
                Map<String, Object> map = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token.replace(token_prefix, ""))
                        .getBody();

                String userName = ((String) map.get("userName"));
                int time = ((int) map.get("exp"));
                String val = template.opsForValue().get(key_prefix + userName);

                if (val == null) {
                    throw new RuntimeException("Unauthorized");
                }
                if (!val.equals(token)) {
                    logger.info("val = {}", val);
                    logger.info("token = {}", token);
                    throw new RuntimeException("Token incorrect");
                }
                if (time < System.currentTimeMillis() / 1000) {    //÷1000是为了单位对齐……
                    logger.info("expire time = {}", time);
                    logger.info("current time = {}", System.currentTimeMillis() / 1000);
                    throw new RuntimeException("Token expired");
                }

            } else {
                throw new RuntimeException("Missing token");
            }
        } else {
            logger.info("unprotected request : {}", uri);
        }
    }
}
