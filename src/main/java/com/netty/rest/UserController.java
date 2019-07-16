package com.netty.rest;

import com.netty.domain.repository.UserRepository;
import com.netty.application.applicationservice.UserService;
import com.netty.domain.User;
import com.netty.domain.vo.UserInfo;
import com.netty.instruction.Constant;
import com.netty.instruction.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author ：冉野
 * @date ：Created in 2019-07-13 14:29
 * @description：用户前端展示层Controller
 * @modified By：
 * @version: V1.0.0$
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户模块")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "接口测试")
    @GetMapping(path = "/test")
    public String test(){
        return "isOk";
    }

    @ApiOperation(value = "用户登录")
    @PostMapping(path = "/login")
    public ResponseResult login(@RequestParam(value = "username") String username, @RequestParam(value = "password")String password, HttpServletRequest request) {
        log.info("用户:[{}]正在登录聊天室",username);
        return userService.login(username,password,request);
    }

    @ApiOperation(value = "通过id获取用户信息")
    @GetMapping(path = "/userInfo")
    public ResponseResult getUserInfoById(HttpServletRequest request){
        Long userId = (Long) request.getSession().getAttribute(Constant.USER_TOKEN);
        log.info("用户:[{}]正在获取用户信息",userId);
        return userService.getUserInfoById(userId);
    }

}
