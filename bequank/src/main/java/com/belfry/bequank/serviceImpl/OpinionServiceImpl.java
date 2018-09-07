package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.mongo.Posting;
import com.belfry.bequank.entity.mongo.Sentiment;
import com.belfry.bequank.entity.primary.User;
import com.belfry.bequank.entity.secondary.Summary;
import com.belfry.bequank.repository.mongo.PostingRepository;
import com.belfry.bequank.repository.mongo.SentiRepository;
import com.belfry.bequank.repository.primary.UserRepository;
import com.belfry.bequank.repository.secondary.SummaryRepository;
import com.belfry.bequank.repository.secondary.Word_tfRepository;
import com.belfry.bequank.service.OpinionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 舆情分析
 * @author Mr.Wang
 * @version 2018/9/6
 */
@Service
public class OpinionServiceImpl implements OpinionService {

    @Resource
    private SummaryRepository summaryRepository;

    @Resource
    private Word_tfRepository wordsRepository;

    @Resource
    private SentiRepository sentiRepository;

    @Resource
    private PostingRepository postingRepository;

    @Resource
    private UserRepository userRepository;

    /**
     * 根据页数得到8篇文章
     * @author Mr.Wang
     * @param page 页号
     * @return net.sf.json.JSONObject
     */
    @Override
    public JSONObject getArticlesByPages(int page){
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(page, 8, sort);
        Page<Summary> articles = summaryRepository.findAll(pageable);
        int totalPages = articles.getTotalPages();
        int currentPage = pageable.getPageNumber();

        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        List<Summary> summaryList = articles.getContent();
        for (Summary summary : summaryList) {
            JSONObject object = new JSONObject();
            object.put("title", summary.getTitle());
            object.put("time", summary.getDate());
            object.put("link", summary.getLink());
            object.put("pos", summary.getPos());
            array.add(object);
        }

        json.put("totalPage", totalPages);
        json.put("currentPage", currentPage);
        json.put("data", array);

        return json;
    }

    /**
     * 政府热点词汇
     * @author Mr.Wang
     * @param () null
     * @return net.sf.json.JSONArray
     */
    @Override
    public JSONArray getGvnHotWords() {

        return null;
    }

    /**
     * 获得关键词云
     * @return net.sf.json.JSONArray
     * @author Mr.Wang
     */
    @Override
    public JSONArray getKeywords() {
        HashMap<String, Integer> map = sentiRepository.getKeywords();
        JSONArray array = new JSONArray();
        if (map == null) {
            JSONObject object = new JSONObject();
            object.put("word", null);
            object.put("count", null);
            array.add(object);
        } else {
            // array = JSONArray.fromObject(map);
            for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
                JSONObject object = new JSONObject();
                object.put("word", entry.getKey());
                object.put("count", entry.getValue());
                array.add(object);
            }
        }
        return array;
    }

    /**
     * 微博热点
     * @param username 用户名
     * @param page     页号
     * @return net.sf.json.JSONArray
     * @author Mr.Wang
     */
    @Override
    public JSONObject getHotSpots(String username, int page) {
        // TODO: 并没有根据用户类型推荐不同新闻的部分，暂时按照全部热点返回
//        User user = userRepository.findByUserName(username);
//        String avatar = user.getAvatar();
        ArrayList<Posting> posts = postingRepository.getHotSpots(page, 8);
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        int totalPage = 0;
        int currentPage = 0;
        if (posts == null) {
            JSONObject object = new JSONObject();
            object.put("username", null);
            object.put("attitudesCount", null);
            object.put("commentCount", null);
            object.put("fullText", null);
            array.add(object);
        } else {
            // TODO: totalPage / currentPage 还未添加
            for (Posting post : posts) {
                JSONObject object = new JSONObject();
                object.put("username", post.getUser());
                object.put("attitudesCount", post.getAttitudes_count());
                object.put("commentCount", post.getComments_count());
                object.put("fullText", post.getFull_text());
                array.add(object);
            }
        }

        json.put("totalPage", totalPage);
        json.put("currentPage", currentPage);
        json.put("data", array);
        return json;
    }

    /**
     * 随机展示三个词的舆情
     * @return net.sf.json.JSONArray
     * @author Mr.Wang
     */
    @Override
    public JSONArray getSentiment() {
        ArrayList<Sentiment> goodSentiment = sentiRepository.getSentiment(1);
        ArrayList<Sentiment> badSentiment = sentiRepository.getSentiment(0);
        ArrayList<Sentiment> allSentiment = new ArrayList<>(goodSentiment);
        allSentiment.addAll(badSentiment);
        int size = allSentiment.size();

        JSONArray array = new JSONArray();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            JSONObject object = new JSONObject();
            int index = random.nextInt(size) + 1;
            Sentiment sentiment = allSentiment.get(index);
            object.put("word", sentiment.getText());
            object.put("sentiment", sentiment.getSenti());
            array.add(object);
        }
        return array;
    }

}
