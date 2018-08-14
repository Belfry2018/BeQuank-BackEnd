package com.belfry.bequank.util;

import com.belfry.bequank.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {
    @Value("${belfry.expiration_time}")
    long expiration_time;
    @Value("${belfry.token_prefix}")
    String token_prefix;
    @Value("${belfry.secret}")
    String secret;
    @Value("${belfry.key_prefix}")
    String key_prefix;

    @Autowired
    StringRedisTemplate template;

    @CachePut( value = "loginList",key = "#user.userName")
    public String generateToken(User user) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("userName", user.getUserName());
        map.put("role", user.getRole());
        String jwt = token_prefix + Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis() + expiration_time))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        template.opsForValue().append(key_prefix + user.getUserName(), jwt);

        return  jwt;
    }

    @Cacheable(value = "loginList", key = "#user.userName")
    public String getToken(User user) {
        return null;
    }

}
