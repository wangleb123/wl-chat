package com.netty.instruction;

import com.netty.instruction.websocket.NettyWebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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

    @Autowired
    private NettyWebSocketServer webSocketServer;

    private Thread nettyThread;

    @PostConstruct
    public void init() {
        nettyThread = new Thread(webSocketServer);
        log.info("netty线程已经开启");
        nettyThread.start();
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
        webSocketServer.close();
        log.info("正在关闭Netty Websocket服务器线程...");
        nettyThread.stop();
        log.info("系统成功关闭！");
    }

}
