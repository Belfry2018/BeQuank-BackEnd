package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.secondary.HYPX;
import com.belfry.bequank.entity.secondary.Summary;
import com.belfry.bequank.entity.secondary.Word_tf;
import com.belfry.bequank.repository.secondary.HYPXRepository;
import com.belfry.bequank.repository.secondary.SummaryRepository;
import com.belfry.bequank.repository.secondary.Word_tfRepository;
import io.jsonwebtoken.lang.Assert;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Resource
    SummaryRepository summaryRepository;
    @Resource
    HYPXRepository HYPXRepository;

    @Test
    public void getGvnPassage() {
//        JSONObject jsonObject = service.getArticlesByPages(0);
//        System.out.println(jsonObject.get("totalPage"));
//        System.out.println(jsonObject.get("currentPage"));
//        JSONArray array = jsonObject.getJSONArray("data");
//        for (int i = 0; i < array.size(); i++) {
//            JSONObject object = array.getJSONObject(i);
//            String pos = object.getString("pos");
//            System.out.println(pos);
//       }
        int page = 0;
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(page, 8, sort);
        Page<Summary> articles  = summaryRepository.findByDateBetween(pageable, "2017-07-01", "2018-10-01");
        List<Summary> summaryList = articles.getContent();

        Page<HYPX> hypxes = HYPXRepository.findComprehensive(pageable, "2018-07-01", "2018-10-01", "china");
        hypxes = HYPXRepository.findAll(pageable);
        List<HYPX> HYPXList = hypxes.getContent();
        System.out.println(HYPXList.size());
        HYPXList.forEach(HYPX -> System.out.println(HYPX.getDate() + " " + HYPX.getPos()));

//        summaryList = summaryRepository.findByDateStartingWith("2018-07-01");
//        summaryList = summaryRepository.findByDateBetween("2018-07-01", "2018-10-01");
//        for (Summary summary:summaryList) {
//            System.out.println(summary.getTitle());
//            System.out.println(summary.getPos());
//            System.out.println(summary.getOrigin());
//            System.out.println(summary.getDate());
//        }
//        System.out.println(summaryList.size());
    }

    @Test
    public void testUnionKey() {
        //System.out.println(wordsRepository.findAll().size());
        List<Word_tf> lists = wordsRepository.findAll();
        for (Word_tf word : lists) {
            System.out.println(word.getWord());
        }
    }

    @Test
    public void testGetKeywords() {
        JSONArray array = service.getKeywords();
        System.out.println(array.toString());
    }

    @Test
    public void testGetSentiment() {
        JSONArray array = service.getSentiment();
        System.out.println(array.toString());
    }

    @Test
    public void testGetSentimentTrend() {
        JSONArray array = service.getSentimentTrend("理念");
        System.out.println(array.toString());
    }

    @Test
    public void testGetGvnHotWords() {
        JSONArray array = service.getGvnHotWords();
        System.out.println(array.toString());
    }

    @Test
    public void getCommentsInSenti() {
        final String word = "刘强东";
        JSONObject object = service.getCommentsInSenti(word);
        System.out.println(word + object.toString());
    }

    @Test
    public void getCommentsInSentiTrend() {
        JSONArray array = service.getSentimentTrend("刘强东");
        System.out.println(array.toString());
        array = service.getCommentsInSentiTrend("刘强东");
        Assert.notEmpty(array);
        System.out.println(array.toString());
    }
}