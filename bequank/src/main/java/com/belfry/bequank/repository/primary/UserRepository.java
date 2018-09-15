package com.belfry.bequank.repository.primary;

import com.belfry.bequank.entity.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.userName=:userName")
    User findByUserName(@Param("userName") String userName);

    @Query("select u from User u where u.id=:id")
    User getById(@Param("id") Long id);
    @Query("select u from User u where u.nickname=:nickname")
    User findByNickname(@Param("nickname")String nickname);
    @Query("update User set nickname=:nickname,avatar=:avatar,phone=:phone,email=:email,gender=:gender,birthday=:birthday,moneyLevel=:moneyLevel,bio=:bio where userName=:userName")
    void setProfile(@Param("userName") String userName,
                    @Param("nickname") String nickname,
                    @Param("avatar") String avatar,
                    @Param("phone") String phone,
                    @Param("email") String email,
                    @Param("gender") String gender,
                    @Param("birthday") String birthday,
                    @Param("moneyLevel") String moneyLevel,
                    @Param("bio") String bio);

    @Query("update User set password=:password where userName=:userName")
    void setPassword(@Param("userName") String userName, @Param("password") String password);
}
