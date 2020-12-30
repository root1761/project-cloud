package com.lnsoft.activiti.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/11/21 11:25
 */
@ApiModel(value = "用户参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserParam {
    @ApiModelProperty(name = "processId", value = "流程id", required = true)
    @NotNull
    private String processId;
    @ApiModelProperty(name="businessKey",value = "业务标识")
   private String businessKey;
    @ApiModelProperty(name ="processDefinitionKey",value = "流程定义key")
    private String processDefinitionKey;
    @ApiModelProperty(name="param",value="bpmn交互参数值")
    private Map<String,Object> param;


}
