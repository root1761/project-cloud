package com.lnsoft.activiti.controller;

import com.lnsoft.activiti.param.TaskQueryParam;
import com.lnsoft.activiti.param.UserParam;
import com.lnsoft.activiti.response.Response;
import com.lnsoft.activiti.service.ActivitiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/10/17 15:03
 */
@Slf4j
@Api(value = "工作流服务")
@RestController
public class ActivityController {
    @Autowired
    private ActivitiService activitiService;

    /**
     * 查询流程定义
     */
    @ApiOperation(value = "查询流程定义", notes = "查询流程定义")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startNum", value = "起始数量"),
            @ApiImplicitParam(name = "endNum", value = "结束数量")

    })
    @GetMapping("/getProcessDefinition")
    public Response getProcessDefinition( Integer startNum, Integer endNum) {
        log.info("ActivityController[]getProcessDefinition[]startNum:{}endNum:{}", startNum, endNum);
        return activitiService.getProcessDefinition( startNum, endNum);
    }

    @ApiOperation(value = "启动流程", notes = "启动流程")
    @PostMapping("/startProcess")
    public Response startProcess(@RequestBody UserParam userParam) {
        log.info("ActivityController[]startProcess[]userParam:{}", userParam);
        return activitiService.startProcess(userParam);
    }

    @ApiOperation(value = "查询任务流程", notes = "查询任务流程")
    @GetMapping("/taskProcessList")
    public Response taskProcessList(@ModelAttribute(name = "taskQueryParam") TaskQueryParam taskQueryParam, @ApiIgnore Authentication authentication) {
        log.info("ActivitiController[]taskProcessList[]taskQueryParam:{}authentication:{}", taskQueryParam,authentication);
        return activitiService.taskProcessList(taskQueryParam,authentication);
    }

    @ApiOperation(value = "候选人拾取并完成任务", notes = "候选人拾取并完成任务")

    @PostMapping("/completeTaskWithGroup/{processInstanceId}")
    public Response completeTaskWithGroup(@PathVariable(name="processInstanceId") String processInstanceId,@RequestBody Map<String,Object> param) {
        log.info("ActivitiController[]completeTaskWithGroup[]processInstanceId:{}param:{}",processInstanceId,param);
        return activitiService.completeTaskWithGroup(processInstanceId,param);
    }

    @ApiOperation(value = "受理人完成任务", notes = "受理人完成任务")
    @PostMapping("/completeTaskWithAssignee/{processInstanceId}")
    public Response completeTaskWithAssignee(@PathVariable(name="processInstanceId") String processInstanceId,@RequestBody Map<String,Object> param) {
        log.info("ActivitiController[]completeTaskWithAssignee[]processInstanceId:{}param:{}", processInstanceId,param);
       return activitiService.completeTaskWithAssignee(processInstanceId,param);
    }

    @ApiOperation(value = "撤回任务", notes = "撤回任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "processInstanceId", value = "实例id", required = true),
    })
    @PostMapping("/backProcessInstance/{processInstanceId}")
    public Response backProcessInstance( @PathVariable(name="processInstanceId") String processInstanceId) {
        log.info("ActivitiController[]backProcessInstance[]processInstanceId:{}", processInstanceId);
        return activitiService.backProcessInstance( processInstanceId);
    }
}
