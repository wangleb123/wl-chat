package com.netty.domain.domainservice;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-14 16:58
 * @description：领域服务-聊天服务
 * @modified By：
 * @version: V1.0.0$
 */
public interface ChatService {

    /**
     * 用户注册登录
     * @param param 参数类型
     * @param ctx 处理上下午文
     */
    void register(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 用户-用户之间相互之间发送消息
     * @param param 参数类型
     * @param ctx 处理上下午文
     */
    void sendOne(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 用户-群之间相互发送消息
     * @param param 参数类型
     * @param ctx 处理上下午文
     */
    void sendGroup(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 用户-用户互发文件
     * @param param 参数类型
     * @param ctx 处理上下午文
     */
    void sendOneFile(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 用户向群发送文件
     * @param param 参数类型
     * @param ctx 处理上下午文
     */
    void sendGroupsFile(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 下线移除
     * @param ctx 处理上下文
     */
    void remove(ChannelHandlerContext ctx);

    /**
     * 不存在该类型业务
     * @param ctx 处理上下文
     */
    void typeError(ChannelHandlerContext ctx);

}
