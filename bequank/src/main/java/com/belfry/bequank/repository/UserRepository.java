package com.belfry.bequank.repository;

import com.belfry.bequank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.userName=:userName")
    User findByUserName(@Param("userName") String userName);
    @Query("select u from User u where u.id=:id")
    User getById(@Param("id")Long id);
}
