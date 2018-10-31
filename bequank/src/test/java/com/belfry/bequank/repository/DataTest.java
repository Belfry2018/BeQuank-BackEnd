package com.belfry.bequank.repository;

import com.belfry.bequank.entity.primary.User;
import com.belfry.bequank.repository.primary.UserRepository;
import com.belfry.bequank.repository.secondary.SummaryRepository;
import com.belfry.bequank.repository.secondary.Word_tfRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 测试数据库
 * @author Mr.Wang
 * @version 2018/8/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DataTest {

    @Resource
    private UserRepository userRepository;

    @Resource
    private SummaryRepository summaryRepository;

    @Resource
    private Word_tfRepository word_tfRepository;

    @Test
    public void testPrimaryDatabase() {

        User user1 = new User();
        user1.setUserName("name1");
        user1.setNickname("nick1");
        User user2 = new User();
        user2.setUserName("name2");
        user2.setNickname("nick2");
        User user3 = new User();
        user3.setUserName("name3");
        user3.setNickname("nick3");

        userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);
        userRepository.saveAndFlush(user3);

        Assert.assertEquals(3, userRepository.findAll().size());
        Assert.assertEquals("nick1", userRepository.findByUserName("name1").getNickname());
    }

    @Test
    public void getPolicy() {
        //Assert.assertNotNull(policyRepository.findByDate("2018"));
        //Summary jbw = jbwRepository.findByDate("2018").get(0);
        int page = 0;
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(page, 8, sort);
        System.out.println(summaryRepository.findByDateStartingWith(pageable,"2018").getContent().get(0).getTitle());
    }

    @Test
    public void testGetWord_tf() {
        System.out.println(word_tfRepository.findByWord("知识产权").size());
    }
//
//    @After
//    public void deleteTestData() {
//        userRepository.delete(userRepository.findByUserName("name1"));
//        userRepository.delete(userRepository.findByUserName("name2"));
//        userRepository.delete(userRepository.findByUserName("name3"));
//    }

}
