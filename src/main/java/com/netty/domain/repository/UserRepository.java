package com.netty.domain.repository;

import com.netty.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-13 14:10
 * @description：用户UserRespository
 * @modified By：
 * @version: v1.0.0$
 */
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * 根据用户密码和账号名称查找用户
     * @param password 用户密码
     * @param userName 用户名
     * @return User实例
     */
    User findByPasswordAndUserName(String password,String userName);

    /**
     * 通过id查询用户
     * @param id 用户id
     * @return User 实例
     */
    User findById(Long id);

    /**
     * 通过用户id查询我的好友列表
     * @param userId
     * @return
     */
    @Query(value = "select * from user u where id in(select friends_id from `user_chat_relation` uc where uc.state = 10 " +
            "and user_id = (:userId) and `group_id` is null) and u.state = 10",nativeQuery = true)
    List<User> findAllByUserId(@Param(value = "userId")Long userId);

}
