package com.lnsoft.gateway.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/01/15 15:13
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    ACCESS_DENIED("Access defined","无权访问"),
    AUTHENTICATION_ENTRYPOIN("Invalid token","token已过期");
    String error;
    String errorDescription;
}
