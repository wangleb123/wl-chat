package com.netty.instruction.websocket;

import com.alibaba.fastjson.JSONObject;
import com.netty.domain.domainservice.ChatService;
import com.netty.domain.domainservice.ChatServiceImpl;
import com.netty.instruction.Constant;
import com.netty.instruction.ResponseResult;
import com.netty.instruction.TypeEnums;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author ：冉野
 * @date ：Created in 2019-07-14 14:40
 * @description： 实际进行处理消息的Handler 发送消息
 * @modified By：
 * @version: V1.0.0
 */
@Slf4j
@Component
@Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

     private ChatService chatService = new ChatServiceImpl();

    /**
     * 读取连接消息并对消息进行处理
     * @param channelHandlerContext 处理上下文
     * @param webSocketFrame WebSocket组件
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame) {
        doHandler(channelHandlerContext,webSocketFrame);
    }

    private void doHandler(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker handsShaker = Constant.webSocketHandshakerMap.get(ctx.channel().id().asLongText());
            if (null == handsShaker) {
                sendErrorMessage(ctx,"该用户已经离线或者不存在该连接");
            }else {
                handsShaker.close(ctx.channel(), ((CloseWebSocketFrame) frame).retain());
            }
            return;
        }
        // ping请求
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (!(frame instanceof TextWebSocketFrame)) {
            sendErrorMessage(ctx,"不支持二进制文件");
        }
        String request = ((TextWebSocketFrame) frame).text();
        JSONObject params = null;
        try {
            params = JSONObject.parseObject(request);
            log.info("收到服务器消息：[{}]",params);
        }catch (Exception e){
            sendErrorMessage(ctx, "JSON字符串转换出错！");
            log.error("参数转换异常");
        }
        if (null == params) {
            sendErrorMessage(ctx, "参数为空！");
            log.warn("参数为空");
            return;
        }

        String messageType = (String) params.get("type");
        switch (messageType) {
            // 注册
            case "REGISTER":
                chatService.register(params,ctx);
                break;
            // 发送消息给单个人
            case "SINGLE_SENDING":
                chatService.sendOne(params, ctx);
                break;
             // 群发消息
            case "GROUP_SENDING":
                //chatService.groupSend(param, ctx);
                break;
             // 发送文件给单个人
            case "FILE_MSG_SINGLE_SENDING":
                //chatService.FileMsgSingleSend(param, ctx);
                break;
             // 群发文件
            case "FILE_MSG_GROUP_SENDING":
                //chatService.FileMsgGroupSend(param, ctx);
                break;
            default:
                //chatService.typeError(ctx);
                break;
        }


    }

    /**
     * 出现不可抗拒因素发送错误消息给客户端
     * @param context 处理上下文
     * @param message 消息文字
     */
    public void sendErrorMessage(ChannelHandlerContext context,String message){
        String result = new ResponseResult().error(message).toString();
        context.channel().writeAndFlush(new TextWebSocketFrame(result));
    }

    /**
     * 客户端断开连接之后触发
     * @param ctx 处理和上下文
     * @throws Exception 捕获异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        chatService.remove(ctx);
    }

    /**
     * 出现移异常后触发
     * @param ctx 处理上下文
     * @param cause 异常类
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
