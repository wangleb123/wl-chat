package com.netty.instruction.websocket;

import com.netty.instruction.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-14 15:12
 * @description：主要是处理Http升级为WebSocket协议
 * @modified By：
 * @version: V1.0.0$
 */
@Component
@Sharable
@Slf4j
public class NettyHttpRequestHandler extends SimpleChannelInboundHandler<Object> {


    /**
     * 主要处理逻辑方法
     * @param context 处理上下文
     * @param msg 接收对象
     */
    @Override
    protected void channelRead0(ChannelHandlerContext context, Object msg) {
        // 如果是Http请求 需要进行协议升级
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(context,(FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            context.fireChannelRead(((WebSocketFrame) msg).retain());
        }

    }

    /**
     * Http协议和转换
     * @param context 处理上下文
     * @param request 消息请求
     */
    private void handleHttpRequest(ChannelHandlerContext context, FullHttpRequest request) {

        if (!request.decoderResult().isSuccess()) {
            sendHttpResponse(context, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        // 协议升级
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory("ws:/" + context.channel() + "/websocket", null, false);
        WebSocketServerHandshaker handsShaker = factory.newHandshaker(request);
        // 存储握手信息
        Constant.webSocketHandshakerMap.put(context.channel().id().asLongText(), handsShaker);
        if (handsShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(context.channel());
        }else {
            // 表示握手成功
            handsShaker.handshake(context.channel(), request);
            log.info("Http-websocket握手协议升级成功啦");
        }
    }

    /**
     * 消息处理失败 发送一个失败请求 应答客户端
     * @param context 处理上下文
     * @param request 请求
     * @param defaultFullHttpResponse 默认的Http响应
     */
    private void sendHttpResponse(ChannelHandlerContext context, FullHttpRequest request, DefaultFullHttpResponse defaultFullHttpResponse) {
        if (defaultFullHttpResponse.status().code() != HttpResponseStatus.OK.code()) {
            ByteBuf buf = Unpooled.copiedBuffer(defaultFullHttpResponse.status().toString(), CharsetUtil.UTF_8);
            defaultFullHttpResponse.content().writeBytes(buf);
            buf.release();
        }
        // 如果长连接好存在 关闭长连接
        boolean keepLive = HttpUtil.isKeepAlive(request);
        ChannelFuture future = context.channel().writeAndFlush(request);
        if (!keepLive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }

    }


    /**
     * 异常处理
     * @param ctx 处理上下文
     * @param cause 抛出异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
