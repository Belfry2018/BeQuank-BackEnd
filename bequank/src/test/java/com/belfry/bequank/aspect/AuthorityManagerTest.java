package com.belfry.bequank.aspect;

import com.belfry.bequank.util.JwtUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorityManagerTest {

    @Autowired
    JwtUtil jwtUtil;

    @Test public void test1() {
        Map<String, Object> map1 = jwtUtil.parseToken(null);
    }

    @Test
    public void test2() {
        Map<String, Object> map2 = jwtUtil.parseToken("sdakjshjksdnja.asdqwd");
    }

    @Test
    public void test3() {
        Map<String, Object> map3 = jwtUtil.parseToken("Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiTk9STUFMIiwidXNlck5hbWUiOiIxNzY3ODU1MTMwQHFxLmNvbSIsImV4cCI6MTUzNjczNzY5NiwidXNlcklkIjoxfQ.g3kgcnw-b4oteAfBk1tFE_w-RR0pxldaFtFVG8gNDHA");
    }
}