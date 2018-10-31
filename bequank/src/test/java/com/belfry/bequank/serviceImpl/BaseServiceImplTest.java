package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.primary.User;
import com.belfry.bequank.repository.primary.UserRepository;
import com.belfry.bequank.util.Role;
import com.belfry.bequank.util.TutorialType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author Mr.Wang
 * @version 2018/10/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BaseServiceImplTest {

    @Autowired
    BaseServiceImpl baseService;

    @Resource
    UserRepository userRepository;

    @Test
    public void addUser() {
        User user = new User("guest", "1234", "guest_nick", null, "1885182000", "test@email.com", "男", null, "千", "帅", null, Role.NORMAL, null, 7, 0.5, 1, 1, true, TutorialType.BEGINNER, false, false);
        userRepository.saveAndFlush(user);
    }

    @Test
    public void testGetAuth() {
        JSONObject object = baseService.getAuth(24);
        System.out.println(object.toString());
    }

    @Test
    public void testUnlockFunction() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", "content");
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("arrayContent1");
        jsonArray.add("arrayContent2");
        jsonObject.put("key2", jsonArray);
        System.out.println(jsonObject.toString());
        if (!jsonObject.getString("key2").equals("xxx")) {
            System.out.println(jsonObject.getString("key2"));
        }
    }
}
