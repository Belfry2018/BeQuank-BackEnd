package com.belfry.bequank.serviceImpl;

import com.belfry.bequank.entity.primary.Comment;
import com.belfry.bequank.entity.primary.Tutorial;
import com.belfry.bequank.entity.primary.User;
import com.belfry.bequank.repository.primary.CommentRepository;
import com.belfry.bequank.repository.primary.TutorialRepository;
import com.belfry.bequank.repository.primary.UserRepository;
import com.belfry.bequank.service.UserService;
import com.belfry.bequank.util.HttpHandler;
import com.belfry.bequank.util.Message;
import com.belfry.bequank.util.Role;
import com.belfry.bequank.util.TutorialType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public JSONArray filterTutorials(String keyword,String tutorialType) {
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
        String[] keywords=keyword.split(" ");
        List<Tutorial> tutorialList=tutorialRepository.getAll();
        //check by id
        Iterator<Tutorial> iter = tutorialList.iterator();
        for(int walker=0;walker<tutorialList.size();walker++){
            Tutorial t=tutorialList.get(walker);
            if(!tutorialType.equals("")&&(!tutorialType.equals(t.getType()))){
                tutorialList.remove(walker);
                walker--;
            }
            // TODO: 8/17/18 optimize keyword filter, making the results sorted by number of hits
            else if(keywords.length>=1){
                boolean hit = false;
                for (int i = 0; i < keywords.length; i++) {
                    for (int j = 0; j < t.getTitle().split(" ").length; j++) {
                        if(keyword.equals(""))hit=true;
                        else {
                            if (t.getTitle().split(" ")[j].contains(keywords[i])) hit = true;
                            if (keywords[i].equals("")) hit = false;
                        }
                    }
                }
                if (!hit) {
                    tutorialList.remove(walker);
                    walker--;
                }
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

    /**
     * 判断用户是否有权限看文章
     * @param pre
     * @param req
     * @return
     */
    private boolean hasPrevilege(String pre, String req) {
        if (pre.equals(TutorialType.ADVANCED)) {
            return true;
        } else if (pre.equals(TutorialType.INTERMEDIATE)) {
            return req.equals(TutorialType.INTERMEDIATE) || req.equals(TutorialType.BEGINNER);
        } else return req.equals(TutorialType.BEGINNER);
    }

    @Override
    public JSONArray recommendation(long userId) {

        String pre = userRepository.getById(userId).getTutorialType();

        List<Tutorial> list = tutorialRepository.getAll().stream().filter(x -> hasPrevilege(pre, x.getType())).collect(Collectors.toList());
        Collections.sort(list);
        JSONArray resultarray = new JSONArray();
        for (Tutorial t : list) {
            JSONObject object = new JSONObject();
            object.put("tutorialId", t.getId());
            object.put("title", t.getTitle());
            object.put("authorNickname", t.getNickname());
            object.put("publishTime", t.getTime());
            object.put("abstract", t.getDescription());
            object.put("cover", t.getCover());
            object.put("tutorialType", t.getType());
            resultarray.add(object);
        }
        return resultarray;
    }

    @Override
    public JSONObject getUnreadMessage(Long userid) {
        List<Comment> list=commentRepository.getUnreadReplies(userid);
        JSONArray jsonArray=new JSONArray();
        for(Comment c:list){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("responseId",c.getId());
            jsonObject.put("courseId",c.getTutorial().getId());
            jsonObject.put("nickname",c.getWriter().getNickname());
            jsonObject.put("comment",c.getContent());
            jsonArray.add(jsonObject);
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("list",jsonArray);
        return jsonObject;
    }

    @Override
    public void readMessage(Long userid, Long responseid) {
        Comment c=commentRepository.getOne(responseid);
        c.setAlreadyread(true);
        commentRepository.save(c);
    }

    @Override
    public JSONArray getDalaos() {

        List<User> list=userRepository.getDalaos(Role.ADVANCED);
        if(list.size()==0){
            return new JSONArray();
        }
        JSONArray jsonArray=new JSONArray();
        for (User u : list){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("avatar",u.getAvatar());
            jsonObject.put("username",u.getUserName());
            jsonObject.put("bio",u.getBio());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public JSONObject getTutorial(Long userid,Long id) {

        Tutorial t=tutorialRepository.getOne(id);
        System.out.println("user "+" is "+userRepository.getById(t.getUserid()));
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("author",(userRepository.getById(t.getUserid())).toJSONObject());
        jsonObject.put("title",t.getTitle());
        jsonObject.put("cover",t.getCover());
        jsonObject.put("abstract",t.getDescription());
        jsonObject.put("keyWords",t.getKeywords());
        jsonObject.put("content",t.getContent());
        jsonObject.put("time",t.getTime());
        int size=0;
        if(t.getLikedlist()!=null)size=t.getLikedlist().size();
        jsonObject.put("likeCount",size);
        if(t.getLikedlist()==null||t.getLikedlist().size()==0)System.out.println("now nobody likes you");
        else System.out.println("pressed like, and result is "+((t.getLikedlist().indexOf(userid))==-1?false:true)+";"+userid+";"+t.getLikedlist().indexOf((long)4));
        if(userid==null)jsonObject.put("alreadyLike",false);
        else jsonObject.put("alreadyLike",t.getLikedlist()==null?false:(t.getLikedlist().indexOf(userid)==-1?false:true));
        jsonObject.put("tutorialType",t.getType());
        List<Comment> list=t.getComments();
        System.out.println("comment number:"+list.size());
        for(Comment c:list){
            c.setAlreadyLiked(c.getLikedusers()==null?false:(c.getLikedusers().indexOf(userid)==-1?false:true));
        }
        JSONArray jsonArray=new JSONArray();
        for(Comment c:list){
            if(c.getReplyTarget()==null)
                jsonArray.add(c.toJSONObject(userid));
        }
        jsonObject.put("comments",jsonArray);
        System.out.println("jsonobject is "+jsonObject);
        return jsonObject;
    }

    @Override
    public JSONObject postComment(Long writerid,Long tutorialid,String content,String time) {
        Comment c=new Comment();
        c.setContent(content);
        c.setWriter(userRepository.getById(writerid));
        c.setNickname(userRepository.getById(writerid).getNickname());
        c.setTime(time);
        Tutorial t=tutorialRepository.getOne(tutorialid);
        c.setTutorial(t);
        t.getComments().add(c);
        System.out.println("comment is "+c.getContent()+";and t comment num is:"+t.getComments().size());
        commentRepository.save(c);
        tutorialRepository.save(t);
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
//        System.out.println("now "+tutorialRepository.getOne((long)13).getComments().size());
        return jsonObject;
    }

    @Override
    public JSONObject reply(Long commenterid,Long commentid, String content,  String time) {
        System.out.println("reply now"+commentRepository.getAll().size());
        Comment origin=commentRepository.getOne(commentid);
        Comment reply=new Comment();
        reply.setContent(content);
        reply.setTime(time);
        reply.setWriter(userRepository.getById(commenterid));
        reply.setNickname(userRepository.getById(commenterid).getNickname());
        reply.setReplyTarget(origin);
        reply.setTutorial(origin.getTutorial());
        reply.setReplyTargetUserid(origin.getWriter().getId());
        origin.getComments().add(reply);
//        commentRepository.save(origin);
        commentRepository.save(reply);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("commentId",reply.getId());
        jsonObject.put("tutorialId",reply.getTutorial().getId());
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
        else {t.getLikedlist().remove(likerid);
            t.setLikedlist(new ArrayList<>());
        }
        tutorialRepository.save(t);
        System.out.println("liked num is "+t.getLikedlist().size());
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",Message.MSG_SUCCESS);
        return jsonObject;
    }

    @Override
    public JSONObject likeComment(Long likerid,Long commentid) {
        Comment c=commentRepository.getOne(commentid);
        if(c.getLikedusers()==null)c.setLikedusers(new ArrayList<>());
        if(c.getLikedusers()==null||c.getLikedusers().indexOf(likerid)==-1)c.getLikedusers().add(likerid);
        else {c.getLikedusers().remove(likerid);
            c.setLikedusers(new ArrayList<>());
        }
        commentRepository.save(c);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("code",Message.MSG_SUCCESS);
        return jsonObject;
    }



}
