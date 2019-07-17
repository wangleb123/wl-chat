package com.netty.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-13 17:27
 * @description：用户聊天关系实体层，包括用户与还有这件关系，与群之间关系
 * @modified By：
 * @version: V1.0.0$
 */
@Entity(name = "user_chat_relation")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserChatRelation {


    /**
     * 主键自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;


    /**
     * 好友id
     */
    @Setter
    @Getter
    @Column(name = "friends_id", columnDefinition = "bigint(19) DEFAULT NULL COMMENT '好友id'")
    private Long friendsId;

    /**
     * 个人id
     */
    @Setter
    @Getter
    @Column(name = "user_id", columnDefinition = "bigint(19) DEFAULT NULL COMMENT '个人id'")
    private Long userId;

    /**
     * 群组id
     */
    @Setter
    @Getter
    @Column(name = "group_id", columnDefinition = "bigint(19) DEFAULT NULL COMMENT '群组id'")
    private Long groupId;

    /**
     * 群组或者好友备注
     */
    @Setter
    @Getter
    @Column(name = "remarks", columnDefinition = "bigint(19) DEFAULT NULL COMMENT '群组或者好友备注'")
    private String remarks;


    /**
     * 创建时间
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
