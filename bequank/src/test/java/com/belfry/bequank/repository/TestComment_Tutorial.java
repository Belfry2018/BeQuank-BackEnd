package com.belfry.bequank.repository;

import com.belfry.bequank.entity.Comment;
import com.belfry.bequank.entity.Tutorial;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestComment_Tutorial {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    TutorialRepository tutorialRepository;

    @Test public void test1() {
        Tutorial tutorial = new Tutorial((long) 1, 109, "nickName", "title", "description", "content", "time", null);
        Comment comment1 = new Comment((long) 1, "content", "time", "nickName", 12, tutorial, null);
        Comment comment2 = new Comment((long) 2, "content", "time", "nickName", 12, tutorial, null);

        tutorialRepository.saveAndFlush(tutorial);
        commentRepository.saveAndFlush(comment1);
        commentRepository.saveAndFlush(comment2);
    }

    @Test
    @Transactional
    public void test2() {
        Tutorial tutorial = tutorialRepository.getOne((long) 2);
        assertEquals(2, tutorial.getComments().size());
    }

    @Test
    @Transactional
    public void test3() {
        Comment comment = commentRepository.getOne((long) 1);
        assertEquals("title", comment.getTutorial().getTitle());
    }
}
