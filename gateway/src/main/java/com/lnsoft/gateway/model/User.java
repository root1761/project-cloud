package com.lnsoft.gateway.model;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户基本信息
 * </p>
 *
 * @author Louyp
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 状态(1:正常，0:禁用)
     */
    private Integer statusId;

    /**
     * 用户编号
     */
    private Long userNo;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 密码盐
     */
    private String mobileSalt;

    /**
     * 登录密码
     */
    private String mobilePsw;

    /**
     * 用户来源(client_id)
     */
    private String userSource;


}
