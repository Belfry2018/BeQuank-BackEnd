package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.secondary.Word_tf;
import com.belfry.bequank.repository.secondary.Word_tfRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OpinionServiceImplTest {

    @Autowired
    OpinionServiceImpl service;

    @Resource
    Word_tfRepository wordsRepository;

    @Test
    public void getGvnPassage() {
        JSONArray array = service.getArticlesByPages(0);
        System.out.println(array.size());
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            String pos = object.getString("pos");
            System.out.println(pos);
       }
    }

    @Test
    public void testUnionKey() {
        //System.out.println(wordsRepository.findAll().size());
        List<Word_tf> lists = wordsRepository.findAll();
        for (Word_tf word : lists) {
            System.out.println(word.getWord());
        }
    }
}