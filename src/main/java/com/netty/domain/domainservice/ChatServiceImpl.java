package com.netty.domain.domainservice;

import com.alibaba.fastjson.JSONObject;
import com.netty.instruction.Constant;
import com.netty.instruction.ResponseResult;
import com.netty.instruction.TypeEnums;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-14 17:02
 * @description：领域服务-聊天实现类
 * @modified By：
 * @version: V1.0.0$
 */
@Service
@Slf4j
public class ChatServiceImpl implements ChatService {



    /**
     * 用户注册登录
     * @param param 参数类型
     * @param ctx 处理上下午文
     */
    @Override
    public void register(JSONObject param, ChannelHandlerContext ctx) {
        String userId = String.valueOf(param.get("userId"));
        log.info("用户：[{}]进行登录注册",userId);
        // 在线注册表添加用户
        Constant.onlineUserMap.put(userId, ctx);
        String registerResult = JSONObject.toJSONString(new ResponseResult().success().setData("type", TypeEnums.REGISTER.name()));
        // 发送消息给客户端
        log.info("注册信息：[{}]",registerResult);
        sendMessageToClient(ctx,registerResult);
        log.info("当前用户：[{}]已经上线，在线人数：[{}]",userId,Constant.onlineUserMap.size());
    }



    /**
     * 用户-用户之间相互之间发送消息
     * @param param 参数类型
     * @param ctx 处理上下午文
     */
    @Override
    public void sendOne(JSONObject param, ChannelHandlerContext ctx) {

        String fromUserId = String.valueOf(param.get("fromUserId"));
        String toUserId = String.valueOf(param.get("toUserId"));
        String content = String.valueOf(param.get("content"));
        ChannelHandlerContext toUserCtx = Constant.onlineUserMap.get(toUserId);
        if (toUserCtx == null) {
            String responseJson = JSONObject.toJSONString(new ResponseResult()
                    .error(MessageFormat.format("userId为 {0} 的用户没有登录！", toUserId)));
            sendMessageToClient(ctx, responseJson);
            log.info("该用户还没登录,请先登录");
        } else {
            String responseResult = JSONObject.toJSONString(new ResponseResult().success()
                    .setData("fromUserId", fromUserId)
                    .setData("content", content)
                    .setData("type", TypeEnums.SINGLE_SENDING));
            sendMessageToClient(toUserCtx, responseResult);
            log.info("发送消息给:[{}],消息内容为:[{}]",toUserCtx,responseResult);
        }
    }

    /**
     * 用户-群之间相互发送消息
     * @param param 参数类型
     * @param ctx 处理上下午文
     */
    @Override
    public void sendGroup(JSONObject param, ChannelHandlerContext ctx) {

    }

    /**
     * 用户-用户互发文件
     * @param param 参数类型
     * @param ctx 处理上下午文
     */
    @Override
    public void sendOneFile(JSONObject param, ChannelHandlerContext ctx) {

    }

    /**
     * 用户向群发送文件
     * @param param 参数类型
     * @param ctx 处理上下午文
     */
    @Override
    public void sendGroupsFile(JSONObject param, ChannelHandlerContext ctx) {

    }

    /**
     * 下线移除
     * @param ctx 处理上下文
     */
    @Override
    public void remove(ChannelHandlerContext ctx) {

    }

    /**
     * 不存在该类型业务
     * @param ctx 处理上下文
     */
    @Override
    public void typeError(ChannelHandlerContext ctx) {

    }

    private void sendMessageToClient(ChannelHandlerContext context, String msg) {
        // 此处一定要注意 不是直接使用context.writeAndFlush()方法
        context.channel().writeAndFlush(new TextWebSocketFrame(msg));
    }
}
