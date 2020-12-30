package com.lnsoft.activiti.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotNull;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/11/21 15:57
 */
@ApiModel(value="流程节点查询对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskQueryParam {
    @ApiModelProperty(name="processInstanceId",value = "流程实例id")
    private String processInstanceId;
    @ApiModelProperty(name="businessKey",value = "业务实例key")
    private String businessKey;
    @ApiModelProperty(name = "taskDefinitionKey",value = "任务key")
    private String taskDefinitionKey;
    @ApiModelProperty(name="id",value = "任务id")
    private String id;
    @ApiModelProperty(name="name",value = "任务名称")
    private String name;
    @ApiModelProperty(name="startNum",value = "起始数量")
    private Integer startNum;
    @ApiModelProperty(name="endNum",value = "结束数量")
    private Integer endNum;
}
