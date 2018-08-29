package com.belfry.bequank.repository;

import com.belfry.bequank.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 5:33 PM 8/17/18
 * @Modifiedby:
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}