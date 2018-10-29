package com.belfry.bequank.repository.primary;

import com.belfry.bequank.entity.primary.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 5:33 PM 8/17/18
 * @Modifiedby:
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query("select m from Comment m")
    List<Comment> getAll();
    @Query("select m from Comment m where m.replyTargetUserid=:userid and m.alreadyread=0")
    List<Comment> getUnreadReplies(@Param("uesrid")Long userid);
}
