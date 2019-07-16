package com.netty.application;

import com.netty.application.applicationservice.UserService;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.netty.domain.Group;
import com.netty.domain.User;
import com.netty.domain.repository.GroupsRepository;
import com.netty.domain.repository.UserRepository;
import com.netty.domain.vo.UserInfo;
import com.netty.instruction.Constant;
import com.netty.instruction.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-13 14:22
 * @description：wwww
 * @modified By：
 * @version: 22$
 */
@Service
@Slf4j
public class UserApplicationServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupsRepository groupsRepository;
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录信息-token
     */
    @Override
    public ResponseResult login(String username, String password, HttpServletRequest request) {

        Preconditions.checkNotNull(username,"用户名为空");
        Preconditions.checkArgument(password.length() > 0, "密码长度必须大于0");
        User user = userRepository.findByPasswordAndUserName(password, username);
        if (Objects.isNull(user)) {
            return new ResponseResult().setStatus(600).setMsg("请登录");
        }
        // 通过HttpSession 存入用户id
        request.getSession().setAttribute(Constant.USER_TOKEN,user.getId());
        return new ResponseResult().setStatus(HttpStatus.OK.value()).success("登录成功");
    }


    /**
     * 通过用户id获取用户信息
     * @param id 用户id
     * @return User 实例
     */
    @Override
    public User getById(Long id) {
        Preconditions.checkNotNull(id,"用户id为空");
        return userRepository.findById(id);
    }

    /**
     * 通过用户id查找用户相关信息 包括群消息 好友列表
     * @param userId 用户id
     * @return 用户基本信息和群信息
     */
    @Override
    public ResponseResult getUserInfoById(Long userId) {
        Preconditions.checkNotNull(userId,"用Id为空");
        User user = userRepository.findById(userId);
        List<Group> groups = groupsRepository.findAllByUserId(userId);
        List<User> friends = userRepository.findAllByUserId(userId);
        UserInfo userInfo = UserInfo.builder()
                .avatarUrl(user.getAvatarUrl())
                .friendList(friends)
                .groupList(groups)
                .userId(userId)
                .userName(user.getUserName())
                .build();
        return new ResponseResult().success().setData("userInfo",userInfo);
    }

}
