package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.primary.Comment;
import com.belfry.bequank.entity.primary.Tutorial;
import com.belfry.bequank.repository.primary.CommentRepository;
import com.belfry.bequank.repository.primary.TutorialRepository;
import com.belfry.bequank.service.UserService;
import com.belfry.bequank.util.Message;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 4:55 PM 8/16/18
 * @Modifiedby:
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    TutorialRepository tutorialRepository;
    @Autowired
    CommentRepository commentRepository;
    @Override
    public JSONArray filterTutorials(Long userid, String time, String cover,String title, String description, String[] keywords,String type) {
        /**
         * @author: Yang Yuqing
         * @description:
         * @param userid
        * @param time
        * @param title
         * @description:    just f**ked this method up... trying to fix it
         *                  I am really worried that this method can't hold massive data...
         *                  Maybe we can split the keyword-search and filter search methods
         *                  8/16/18
         *                  Problem solved.
         *                  8/17/2018
        * @param keywords
         * @return net.sf.json.JSONArray
         * @date: 5:05 PM 8/16/18
         */
        System.out.println("s");
        List<Tutorial> tutorialList=tutorialRepository.findAll();
        //check by id
        Iterator<Tutorial> iter = tutorialList.iterator();
        JSONArray keywordlist=JSONArray.fromObject(keywords);
        while(iter.hasNext()){
            Tutorial t=iter.next();
            if(userid!=null&&t.getUserid()!=userid)
                iter.remove();
            else if(time!=null&&!t.getTime().equals(time))
                iter.remove();
            else if(title!=null&&!t.getTitle().equals(title))
                iter.remove();
            else if(description!=null&&!t.getDescription().equals(description)){
                iter.remove();
            }
            // TODO: 8/17/18 optimize keyword filter, making the results sorted by number of hits
            else if(keywords!=null){
                boolean hit = false;
                for (int i = 0; i < keywordlist.size(); i++) {
                    for (int j = 0; j < t.getKeywords().size(); j++) {
                        if (keywordlist.getString(i).equals(t.getKeywords().getString(j))) hit = true;
                    }
                }
                if (!hit) iter.remove();
            }
        }




        return new JSONArray().fromObject(tutorialList);
    }

    @Override
    public Tutorial getTutorial(Long id) {
        return tutorialRepository.getOne(id);
    }

    @Override
    public JSONObject postComment(Long tutorialid, String content, String nickname, Long writerid, String time) {
        Comment c=new Comment();
        c.setContent(content);
        c.setWriterid(writerid);
        c.setNickname(nickname);
        c.setTime(time);
        Tutorial t=tutorialRepository.getOne(tutorialid);
        t.getComments().add(c);
        tutorialRepository.save(t);
        commentRepository.save(c);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",Message.MSG_SUCCESS);
        return jsonObject;
    }

    @Override
    public JSONObject reply(Long commentid, String content, String nickname, Long writerid, String time) {
        Comment origin=commentRepository.getOne(commentid);
        Comment reply=new Comment();
        reply.setContent(content);
        reply.setTime(time);
        reply.setWriterid(writerid);
        reply.setNickname(nickname);
        origin.getComments().add(reply);
        commentRepository.save(origin);
        commentRepository.save(reply);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",Message.MSG_SUCCESS);
        return jsonObject;
    }

    @Override
    public JSONObject likeTutorial(Long tutorialid, Long likerid) {
        /**
         * @author: Yang Yuqing
         * @description:            Suddenly, I became lost. My lord, what you gave me the likerid for ?
         * @param tutorialid
        * @param likerid
         * @return net.sf.json.JSONObject
         * @date: 5:44 PM 8/17/18
         */
        Tutorial t=tutorialRepository.getOne(tutorialid);
        t.setLikecount(t.getLikecount()+1);
        tutorialRepository.save(t);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",Message.MSG_SUCCESS);
        return jsonObject;        }

    @Override
    public JSONObject likeComment(Long commentid, Long likerid) {
        Comment c=commentRepository.getOne(commentid);
        c.setLikecount(c.getLikecount()+1);
        commentRepository.save(c);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",Message.MSG_SUCCESS);
        return jsonObject;
    }

}
