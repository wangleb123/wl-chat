package com.netty.instruction;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.netty.instruction.websocket.NettyWebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-14 16:26
 * @description：程序上下文
 * @modified By：
 * @version: V1.0.0$
 */
@Component
@Scope("singleton")
@Slf4j
public class ApplicationContext {

    private final NettyWebSocketServer webSocketServer;
    /**
     * newFixedThreadPool和newSingleThreadExecutor:  主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
     * newCachedThreadPool和newScheduledThreadPool:  主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
     */
    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-netty_chat-runner-%d").build();
    ExecutorService executor = new ThreadPoolExecutor(Constant.THEAD_SIZE,Constant.THEAD_SIZE,0L,TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),namedThreadFactory);
    public ApplicationContext(NettyWebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    @PostConstruct
    public void init() {
        executor.submit(webSocketServer);
        log.info("netty线程已经开启");
    }


    /**
     * 描述：Tomcat服务器关闭前需要手动关闭Netty Websocket相关资源，否则会造成内存泄漏。
     *      1. 释放Netty Websocket相关连接；
     *      2. 关闭Netty Websocket服务器线程。（强行关闭，是否有必要？）
     */
    @SuppressWarnings("deprecation")
    @PreDestroy
    public void close() {
        log.info("正在释放Netty Websocket相关连接...");
        executor.shutdown();
    }

}
