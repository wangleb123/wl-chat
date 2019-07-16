package com.netty.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-13 17:53
 * @description：群相关信息  说明一下 之前使用了group作为表名称 直接执行代码 一直提示sql有问题，后来才发现是使用了关键词group 建表时慎用
 * @modified By：
 * @version: V1.0.0$
 */
@Entity(name = "groups")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Group {

    /**
     * 主键自增id 群组id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    /**
     * 群名称
     */
    @Setter
    @Getter
    @Column(name = "group_name",columnDefinition = "varchar(64) DEFAULT NULL COMMENT '群名称'")
    private String groupName;


    /**
     * 群创建人
     */
    @Setter
    @Getter
    @Column(name = "create_user",columnDefinition = "varchar(64) DEFAULT NULL COMMENT '群创建人'")
    private String createUser;


    /**
     * 群公告
     */
    @Setter
    @Getter
    @Column(name = "group_note",columnDefinition = "varchar(64) DEFAULT NULL COMMENT '群公告'")
    private String groupNote;

    /**
     * 群公告
     */
    @Setter
    @Getter
    @Column(name = "group_avatar_url",columnDefinition = "varchar(64) DEFAULT NULL COMMENT '群头像'")
    private String groupAvatarUrl;

    /**
     * 群创建时间
     */
    @Setter
    @Getter
    @Column(name = "create_date", columnDefinition = "datetime DEFAULT NULL COMMENT '创建时间'")
    private Date createDate;

    /**
     * 修改时间
     */
    @Setter
    @Getter
    @Column(name = "modify_date", columnDefinition = "datetime DEFAULT NULL COMMENT '修改时间'")
    private Date modifyDate;


    /**
     * 状态
     */
    @Setter
    @Getter
    @Column(name = "state", columnDefinition = "int(4) DEFAULT NULL COMMENT '删除标志位'")
    private Integer state;









}
