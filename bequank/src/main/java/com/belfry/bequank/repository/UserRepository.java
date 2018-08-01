package com.belfry.bequank.repository;

import com.belfry.bequank.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

/**
 * @Author: Yang Yuqing
 * @Description:
 * @Date: Created in 10:19 AM 8/1/18
 * @Modifiedby:
 */

@Repository
@Table(name="user")
@Qualifier("userRepository")
public interface UserRepository extends CrudRepository<User, Long > {
    @Query("select t from User t where t.id=:id")
    public User findOne(@Param("id") Long id);

    @Modifying
    @SuppressWarnings("unchecked")
    public User save(User user);

    @Query("select u from User u where u.username=:username")
    public User findByUsername(@Param("username") String username);

    @Query("select u from User u where u.username=:username")
    public User findByEmail(@Param("username") String username);

    @Query("update User u set email=:newemail where u.username=:username")
    public User modfyEmail(@Param("username") String username, @Param("newemail") String newemail);

    @Query("select u from User u where u.role=:role")
    public List<User> getUserList(@Param("role") String role);

    @Query("select u from User u")
    public List<User> getAll();

    @Query("select count(u) from User u where u.role=:role")
    public int getUserNumByRole(@Param("role") String role);
}