package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.Tutorial;
import com.belfry.bequank.entity.User;
import com.belfry.bequank.repository.CommentRepository;
import com.belfry.bequank.repository.TutorialRepository;
import com.belfry.bequank.repository.UserRepository;
import com.belfry.bequank.service.SystemUserService;
import com.belfry.bequank.service.UserService;
import com.belfry.bequank.util.Role;
import net.sf.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 5:48 PM 8/17/18
 * @Modifiedby:
 */

public class UserServiceImplTest {
    @Autowired
    TutorialRepository tutorialRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    SystemUserService systemUserService;
    @Autowired
    UserService userService;
    @Before
    public void prepare(){
        User u=new User();
        u.setUserName("bequank@outlook.com");
        u.setPassword("citi@2018");
        u.setNickName("bequank");
        u.setRole(Role.NORMAL);
        System.out.println(userRepository);
        userRepository.save(u);

        User u1=new User();
        u1.setUserName("123@outlook.com");
        u1.setPassword("666");
        u1.setNickName("user");
        u1.setRole(Role.NORMAL);
        userRepository.save(u1);
        String[] tags={"intro","bequank","first"};
        JSONArray array=JSONArray.fromObject(tags);

        systemUserService.postTutorial(u.getNickName(),
                u.getId(),
                "bequank introduction",
                "describes bequank intro",
                array,
                "Bequank financial platform is our best financial platform ever.",
                "2018-8-17"
                );

    }
    @Test
    public void filterTutorials() {

    }

    @Test
    public void getTutorial() {
        System.out.println(userService.getTutorial(tutorialRepository.findByTitle("bequank introduction").getId()));
    }

    @Test
    public void postComment() {

    }

    @Test
    public void reply() {
    }

    @Test
    public void likeTutorial() {
    }

    @Test
    public void likeComment() {
    }
}