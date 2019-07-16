package com.netty.instruction;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-14 13:46
 * @description：发送消息枚举类型
 * @modified By：
 * @version: V1.0.0$
 */
@AllArgsConstructor
@Getter
public enum TypeEnums {

    REGISTER("注册"),
    SINGLE_SENDING("给单个人发送消息"),
    GROUP_SENDING("群发"),
    FILE_MSG_SINGLE_SENDING("给单个人发送文件"),
    FILE_MSG_GROUP_SENDING("群发文件");
    private String type;
}
