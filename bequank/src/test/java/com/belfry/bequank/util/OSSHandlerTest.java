package com.belfry.bequank.util;

import com.belfry.bequank.service.SystemUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OSSHandlerTest {

    @Autowired
    OSSHandler handler;

    @Test
    public void upload() {
        File file = new File("c:/avatar.jpg");
        assertEquals(true, file.exists());
        String url = handler.upload(file);
        assertNotNull(url);
        System.out.println(url);
    }
}