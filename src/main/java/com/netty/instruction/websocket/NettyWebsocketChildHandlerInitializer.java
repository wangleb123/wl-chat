package com.netty.instruction.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-14 15:37
 * @description：初始化ChannelChildHandler连接
 * @modified By：
 * @version: V1.0.0$
 */
@Component
public class NettyWebsocketChildHandlerInitializer extends ChannelInitializer<SocketChannel> {

    private final NettyWebSocketServerHandler webSocketServerHandler;

    private final NettyHttpRequestHandler httpRequestHandler;

    public NettyWebsocketChildHandlerInitializer(NettyWebSocketServerHandler webSocketServerHandler, NettyHttpRequestHandler httpRequestHandler) {
        this.webSocketServerHandler = webSocketServerHandler;
        this.httpRequestHandler = httpRequestHandler;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        // HTTP编解码器
        socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
        // HTTP头和body拼接成完整请求体
        socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        // 大文件传输策略
        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        socketChannel.pipeline().addLast("http-handler", httpRequestHandler);
        socketChannel.pipeline().addLast("websocket-handler", webSocketServerHandler);

    }
}
