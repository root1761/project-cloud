package com.lnsoft.qxgd.model;

import java.time.LocalDateTime;
import java.io.Serializable;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户基本信息
 * </p>
 *
 * @author Louyp
 * @since 2020-11-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    @Excel( name = "创建时间",databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss", isImportField = "true_st", width = 20)
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @Excel( name = "修改时间",databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss", isImportField = "true_st", width = 20)

    private LocalDateTime gmtModified;

    /**
     * 状态(1:正常，0:禁用)
     */
    @Excel(name = "学生性别", replace = { "正常_1", "禁用_0" },  isImportField = "true_st")
    private Integer statusId;

    /**
     * 用户编号
     */
    @Excel(name = "用户编号", isImportField = "true_st")
    private Long userNo;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码", isImportField = "true_st")

    private String mobile;

    /**
     * 密码盐
     */
    @Excel(name = "密码盐", isImportField = "true_st")

    private String mobileSalt;

    /**
     * 登录密码
     */
    private String mobilePsw;

    /**
     * 用户来源(client_id)
     */
    @Excel(name = "用户来源", isImportField = "true_st")

    private String userSource;


}
