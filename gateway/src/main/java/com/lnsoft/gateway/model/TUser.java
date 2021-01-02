package com.lnsoft.gateway.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Louyp
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;
@TableField("id")
    private Long id;

    @TableField("userName")
    private String userName;
@TableField("password")
    private String password;


}
