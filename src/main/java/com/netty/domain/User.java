package com.netty.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：冉野
 * @date ：Created in 2019-07-13 12:14
 * @description：用户实体层
 * @modified By：
 * @version: V1.0.0$
 */
@Entity(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {


    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    /**
     * phone_number(用户手机号)
     */
    @Setter
    @Getter
    @Column(name = "phone_number", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '用户手机号'")
    private String phoneNumber;

    /**
     * phone_number(用户手机号)
     */
    @Setter
    @Getter
    @Column(name = "pass_word", columnDefinition = "varchar(64) DEFAULT NULL COMMENT '用户密码'")
    private String password;

    /**
     * gender(用户性别)
     */
    @Setter
    @Getter
    @Column(name = "gender", columnDefinition = "int(4) DEFAULT NULL COMMENT '用户性别'")
    private String gender;

    /**
     * nick_name(用户昵称)
     */
    @Setter
    @Getter
    @Column(name = "user_name", columnDefinition = "varchar(45) DEFAULT NULL COMMENT '用户昵称'")
    private String userName;

    /**
     * head_image(用户性别)
     */
    @Setter
    @Getter
    @Column(name = "avatar_url", columnDefinition = "varchar(255) DEFAULT NULL COMMENT '用户头像'")
    private String avatarUrl;


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
    @Column(name = "state", columnDefinition = "datetime DEFAULT NULL COMMENT '删除标志位'")
    private Integer state;


    /**
     * 用户状态枚举
     */
    @Getter
    @AllArgsConstructor
    public enum StudentStatusEnum {

        /**
         * GENERAL：正常
         * FREEZE： 冻结
         */
        GENERAL(10),
        FREEZE(20);

        private Integer status;
    }

    /**
     * 冻结用户
     */
    public void freeze() {
        Integer status = User.StudentStatusEnum.FREEZE.getStatus();
        this.state = status.equals(this.state) ? User.StudentStatusEnum.GENERAL.getStatus() : status;
        //this.addEvent(new StudentFreezeEvent(this.getId(),this.accountId));
    }


}
