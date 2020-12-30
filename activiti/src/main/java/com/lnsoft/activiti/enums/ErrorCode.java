package com.lnsoft.activiti.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/11/21 11:13
 */
@ToString
@Getter
@AllArgsConstructor
public enum ErrorCode {
    PARAM_IS_NULL("参数为空！！！"),
    START_IS_FAILED("流程启动失败！！！"),
    QUERY_IS_FAILED("流程查询失败！！！"),
    COMPLETE_TASK_IS_FAILED("完成任务失败！！！");

    private String message;

}
