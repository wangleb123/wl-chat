package com.netty.instruction.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-14 13:51
 * @description：使用Netty作为通信基础的Websocket
 * @modified By：
 * @version: V1.0.0$
 */
@Slf4j
@Service
public class NettyWebSocketServer implements Runnable {


    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ServerBootstrap serverBootstrap = new ServerBootstrap();

    private static final int RECVBYTE_ALLOCATOR_SIZE = 592048;
    private static final Integer PORT = 1235;
    @Autowired
    private NettyWebsocketChildHandlerInitializer childChannelHandler;
    private ChannelFuture serverChannelFuture;

    NettyWebSocketServer(){}
    @Override
    public void run() {
        build();
    }

    /**
     * 启动NettyWebSocket
     */
    private void build() {
        Long beginTime = System.currentTimeMillis();
        serverBootstrap.group(bossGroup,workerGroup)
                // 指定是Nio通信服务
                .channel(NioServerSocketChannel.class)
                // TCP参数配置 握手字符串长度设置
                .option(ChannelOption.SO_BACKLOG,1024)
                // 设置TCP NO_DELAY 算法 尽量发送大文件包
                .option(ChannelOption.TCP_NODELAY,true)
                // 开启心跳模式
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                // 配置固定长度接收缓存内存分配
                .childOption(ChannelOption.RCVBUF_ALLOCATOR,new FixedRecvByteBufAllocator(RECVBYTE_ALLOCATOR_SIZE))
                .childHandler(childChannelHandler);
        try {
            serverChannelFuture = serverBootstrap.bind(PORT).sync();
            Long endTime = System.currentTimeMillis();
            log.info("服务器启动完成，耗时:[{}]毫秒,已经在端口：[{}]进行阻塞等待",endTime - beginTime,PORT);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            // 优雅关闭连接
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }


    public void close() {
        serverChannelFuture.channel().close();
        Future<?> bossGroupFuture = bossGroup.shutdownGracefully();
        Future<?> workerGroupFuture = workerGroup.shutdownGracefully();
        try {
            bossGroupFuture.await();
            workerGroupFuture.await();
        }catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public ChannelHandler getChildChannelHandler() {
        return childChannelHandler;
    }


}
