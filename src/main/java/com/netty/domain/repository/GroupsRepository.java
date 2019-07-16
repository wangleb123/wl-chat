package com.netty.domain.repository;

import com.netty.domain.Group;
import com.netty.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-13 18:26
 * @description：群组仓储层
 * @modified By：
 * @version: V1.0.0$
 */
public interface GroupsRepository extends JpaRepository<Group,Long> {


    /**
     * 通过用户id查询我的群组列表
     * @param userId
     * @return 群组列表
     */
    @Query(value = "select * from groups g where id in(select group_id from `user_chat_relation` uc where uc.state = 10 \n" +
            "            and user_id = (:userId) and `friends_id` is null) and g.state = 10",nativeQuery = true)
    List<Group> findAllByUserId(@Param(value = "userId") Long userId);
}
