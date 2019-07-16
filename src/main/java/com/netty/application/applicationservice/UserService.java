package com.netty.application.applicationservice;

import com.netty.domain.User;
import com.netty.domain.vo.UserInfo;
import com.netty.instruction.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-13 14:24
 * @description：用户接口服务层
 * @modified By：
 * @version: V1.0.0$
 */
public interface UserService {

    /**
     * 用户登录
     * @param nickName 用户名
     * @param password 密码
     * @param request 请求会话
     * @return 登录信息-token
     */
    @PostMapping(path = "/user/login",name = "用户登录")
    ResponseResult login(@RequestParam String nickName, @RequestParam String password, HttpServletRequest request);

    /**
     * 通过用户id获取用户信息
     * @param id 用户id
     * @return User 实例
     */
    @GetMapping(path = "/user",name = "获取用户信息")
    User getById(Long id);

    /**
     * 通过用户id查找用户相关信息 包括群消息 好友列表
     * @param userId 用户id
     * @return 用户基本信息和群信息
     */
    ResponseResult getUserInfoById(Long userId);



}
