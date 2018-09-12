package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.primary.Comment;
import com.belfry.bequank.entity.primary.Tutorial;
import com.belfry.bequank.repository.primary.CommentRepository;
import com.belfry.bequank.repository.primary.TutorialRepository;
import com.belfry.bequank.repository.primary.UserRepository;
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
    @Autowired
    UserRepository userRepository;
    @Override
    public JSONArray filterTutorials(String[] keywords,String tutorialType) {
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

        List<Tutorial> tutorialList=tutorialRepository.getAll();
        //check by id
        Iterator<Tutorial> iter = tutorialList.iterator();
        JSONArray keywordlist=JSONArray.fromObject(keywords);
        System.out.println("keywordlaength"+keywordlist.size());
        while(iter.hasNext()){
            Tutorial t=iter.next();

//            else if(time!=null&&!t.getTime().equals(time))
//                iter.remove();
//            else if(title!=null&&!t.getTitle().equals(title))
//                iter.remove();
//            else if(description!=null&&!t.getDescription().equals(description)){
//                iter.remove();
//            }
            // TODO: 8/17/18 optimize keyword filter, making the results sorted by number of hits
            if(keywords.length>=2){
                boolean hit = false;
                for (int i = 0; i < keywordlist.size(); i++) {
                    for (int j = 0; j < t.getKeywords().size(); j++) {
                        if (keywordlist.getString(i).equals(t.getKeywords().getString(j))) hit = true;
                    }
                }
                if (!hit) iter.remove();
            }
        }
        JSONArray resultarray=new JSONArray();
        for(Tutorial t:tutorialList){
            JSONObject object=new JSONObject();
            object.put("tutorialId",t.getId());
            object.put("title",t.getTitle());
            object.put("authorNickname",t.getNickname());
            object.put("publishTime",t.getTime());
            object.put("abstract",t.getDescription());
            object.put("cover",t.getCover());
            object.put("tutorialType",t.getType());
            resultarray.add(object);
        }
        System.out.println("result of tutorials is "+resultarray);



        return resultarray;
    }

    @Override
    public JSONObject getTutorial(Long userid,Long id) {

        Tutorial t=tutorialRepository.getOne(id);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("author",userRepository.getById(t.getUserid()));
        jsonObject.put("title",t.getTitle());
        jsonObject.put("cover",t.getCover());
        jsonObject.put("abstract",t.getDescription());
        jsonObject.put("keyWords",t.getKeywords());
        jsonObject.put("content",t.getContent());
        jsonObject.put("time",t.getTime());
        int size=0;
        if(t.getLikedlist()!=null)size=t.getLikedlist().size();
        jsonObject.put("likecount",size);
        if(userid==null)jsonObject.put("alreadyLike",false);
        else jsonObject.put("alreadyLike",t.getLikedlist()==null?false:(t.getLikedlist().indexOf(userid)==-1?false:true));
        jsonObject.put("tutorialType",t.getType());
        List<Comment> list=t.getComments();
        for(Comment c:list){
            c.setAlreadyLiked(c.getLikedusers()==null?false:(c.getLikedusers().indexOf(userid)==-1?false:true));
        }
        jsonObject.put("comments",list);
        return jsonObject;
    }

    @Override
    public JSONObject postComment(Long writerid,Long tutorialid,String content,String time) {
        Comment c=new Comment();
        c.setContent(content);
        c.setWriterid(writerid);
        c.setNickname(userRepository.getById(writerid).getNickname());
        c.setTime(time);
        Tutorial t=tutorialRepository.getOne(tutorialid);
        t.getComments().add(c);
        tutorialRepository.save(t);
        commentRepository.save(c);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("commentId",c.getId());
        jsonObject.put("tutorialId",t.getId());
        jsonObject.put("fatherComment",null);
        jsonObject.put("writer",userRepository.getById(writerid));
        jsonObject.put("content",content);
        jsonObject.put("time",time);
        jsonObject.put("likeCount",c.getLikedusers()==null?0:c.getLikedusers().size());
        jsonObject.put("alreadyLike",c.getLikedusers()==null?false:(c.getLikedusers().indexOf(writerid)==-1?false:true));
        jsonObject.put("childrenComments",null);
        return jsonObject;
    }

    @Override
    public JSONObject reply(Long commenterid,Long commentid, String content,  String time) {
        Comment origin=commentRepository.getOne(commentid);
        Comment reply=new Comment();
        reply.setContent(content);
        reply.setTime(time);
        reply.setWriterid(commenterid);
        reply.setNickname(userRepository.getById(commenterid).getNickname());
        reply.setReplyTarget(origin);
        origin.getComments().add(reply);
        commentRepository.save(origin);
        commentRepository.save(reply);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("commentId",reply.getId());
        jsonObject.put("tutorialId",reply.getId());
        jsonObject.put("fatherComment",null);
        jsonObject.put("writer",userRepository.getById(commenterid));
        jsonObject.put("content",content);
        jsonObject.put("time",time);
        jsonObject.put("likeCount",reply.getLikedusers().size());
        if(reply.getLikedusers()==null)reply.setLikedusers(new ArrayList<>());
        jsonObject.put("alreadyLike",reply.getLikedusers()==null?false:(reply.getLikedusers().indexOf(commenterid)==-1?false:true));
        jsonObject.put("childrenComments",null);
        return jsonObject;
    }

    @Override
    public JSONObject likeTutorial(Long likerid,Long tutorialid) {
        /**
         * @author: Yang Yuqing
         * @description:            Suddenly, I became lost. My lord, what you gave me the likerid for ?
         * @param tutorialid
        * @param likerid
         * @return net.sf.json.JSONObject
         * @date: 5:44 PM 8/17/18
         */
        Tutorial t=tutorialRepository.getOne(tutorialid);
        if(t.getLikedlist()==null)t.setLikedlist(new ArrayList<>());
        if(t.getLikedlist()==null||t.getLikedlist().indexOf(likerid)==-1)t.getLikedlist().add(likerid);
        else t.getLikedlist().remove(likerid);
        tutorialRepository.save(t);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",Message.MSG_SUCCESS);
        return jsonObject;        }

    @Override
    public JSONObject likeComment(Long likerid,Long commentid) {
        Comment c=commentRepository.getOne(commentid);
        if(c.getLikedusers()==null)c.setLikedusers(new ArrayList<>());

        if(c.getLikedusers()==null||c.getLikedusers().indexOf(likerid)==-1)c.getLikedusers().add(likerid);
        else c.getLikedusers().remove(likerid);
        commentRepository.save(c);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",Message.MSG_SUCCESS);
        return jsonObject;
    }

}
