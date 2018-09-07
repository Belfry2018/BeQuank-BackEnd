package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.secondary.Jbw;
import com.belfry.bequank.repository.secondary.JbwRepository;
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
import java.util.List;

/**
 * 舆情分析
 * @author Mr.Wang
 * @version 2018/9/6
 */
@Service
public class OpinionServiceImpl implements OpinionService {

    @Resource
    private JbwRepository jbwRepository;

    @Resource
    private Word_tfRepository wordsRepository;

    /**
     * 根据页数得到8篇文章
     * @author Mr.Wang
     * @param page 页号
     * @return net.sf.json.JSONArray
     */
    @Override
    public JSONArray getArticlesByPages(int page){
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        Pageable pageable = PageRequest.of(page, 8, sort);
        Page<Jbw> articles = jbwRepository.findAll(pageable);

        JSONArray array = new JSONArray();
        List<Jbw> jbwList = articles.getContent();
        for (Jbw jbw:jbwList) {
            JSONObject object = new JSONObject();
            object.put("title", jbw.getTitle());
            object.put("time", jbw.getDate());
            object.put("link", jbw.getLink());
            object.put("pos", jbw.getPos());
            array.add(object);
        }
        return array;
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

}
