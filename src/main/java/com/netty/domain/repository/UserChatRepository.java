package com.netty.domain.repository;

import com.netty.domain.User;
import com.netty.domain.UserChatRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-13 18:28
 * @description：群组仓储层
 * @modified By：
 * @version: V1.0.0$
 */
public interface UserChatRepository extends JpaRepository<UserChatRelation,Long> {


    /**
     * 通过GroupId 查询群
     *
     * @param groupId 群id
     * @return 返回用户群关系对象
     */
    Optional<List<UserChatRelation>> findAllByGroupId(Long groupId);




}
