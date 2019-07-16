package com.netty.domain.vo;

import com.netty.domain.Group;
import com.netty.domain.User;
import lombok.*;

import java.util.List;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-13 18:32
 * @description：用户基本信息VO
 * @modified By：
 * @version: v1.0.0$
 */
@AllArgsConstructor
@Builder
@ToString
public class UserInfo {


    /**
     * 用户id
     */
    @Setter
    @Getter
    private Long userId;

    /**
     * 用户名称
     */
    @Setter
    @Getter
    private String userName;

    /**
     *  用户头像
     */
    @Setter
    @Getter
    private String avatarUrl;

    /**
     *  好友列表
     */
    @Setter
    @Getter
    private List<User> friendList;

    /**
     *  群列表
     */
    @Setter
    @Getter
    private List<Group> groupList;

}
