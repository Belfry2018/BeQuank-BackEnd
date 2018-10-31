package com.belfry.bequank.repository.primary;

import com.belfry.bequank.entity.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.userName=:userName")
    User findByUserName(@Param("userName") String userName);

    @Query("select u from User u where u.id=:id")
    User getById(@Param("id") Long id);

    @Query("select u from User u where u.nickname=:nickname")
    User findByNickname(@Param("nickname")String nickname);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update User u set u.nickname=:nickname,u.avatar=:avatar,u.phone=:phone,u.email=:email,u.gender=:gender,u.birthday=:birthday,u.moneyLevel=:moneyLevel,u.bio=:bio where userName=:userName")
    void setProfile(@Param("userName") String userName,
                    @Param("nickname") String nickname,
                    @Param("avatar") String avatar,
                    @Param("phone") String phone,
                    @Param("email") String email,
                    @Param("gender") String gender,
                    @Param("birthday") String birthday,
                    @Param("moneyLevel") String moneyLevel,
                    @Param("bio") String bio);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update User set password=:password where userName=:userName")
    void setPassword(@Param("userName") String userName, @Param("password") String password);

    @Query(nativeQuery = true, value = "select * from user u where u.role=:role limit 6")
    List<User> getDalaos(@Param("role") String role);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update User u set u.level=?2 where u.id=?1")
    void updateLevel(long userId, String level);
}
