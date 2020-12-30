package com.lnsoft.activiti.service.impl;

import com.lnsoft.activiti.enums.ErrorCode;
import com.lnsoft.activiti.param.TaskQueryParam;
import com.lnsoft.activiti.param.UserParam;
import com.lnsoft.activiti.response.Response;
import com.lnsoft.activiti.security.UserSecurityUtil;
import com.lnsoft.activiti.service.ActivitiService;
import com.lnsoft.activiti.util.ActivitiUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.model.builders.StartProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/11/21 11:19
 */
@Slf4j
@Service
public class ActivitiServiceImpl implements ActivitiService {
    @Autowired
    private UserSecurityUtil userSecurityUtil;
    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRuntime taskRuntime;

    @Override
    public Response getProcessDefinition( Integer startNum, Integer endNum) {
        log.info("ActivityService[]getProcessDefinition[]startNum:{}endNum:{}", startNum, endNum);

        Pageable pageable = null;
        if (!Objects.isNull(startNum) && !Objects.isNull(endNum)) {
            pageable = Pageable.of(startNum, endNum);
        }
        //查询所有流程定义信息
        try {
            Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(pageable);
            return Response.yes(processDefinitionPage);
        } catch (Exception e) {
            log.error("ActivityService[]getProcessDefinition[]startNum:{}endNum:{}cause:{}", startNum, endNum, e);
            return Response.no(ErrorCode.QUERY_IS_FAILED.getMessage());

        }
    }

    @Override
    public Response startProcess(UserParam userParam) {
        log.info("ActivityService[]getProcessDefinition[]userParam:{}", userParam);
        try {
            final StartProcessPayloadBuilder startProcessPayloadBuilder = ProcessPayloadBuilder.start().withProcessDefinitionKey(userParam.getProcessId()).withVariable("fileContent", "activiti").withVariables(userParam.getParam());
            if (StringUtils.isNotEmpty(userParam.getBusinessKey())) {
                startProcessPayloadBuilder.withBusinessKey(userParam.getBusinessKey());
            }
            if (StringUtils.isNotEmpty(userParam.getProcessDefinitionKey())) {
                startProcessPayloadBuilder.withProcessDefinitionKey(userParam.getProcessDefinitionKey());
            }
            ProcessInstance processInstance = processRuntime.start(startProcessPayloadBuilder.build());
            System.out.println(processInstance.getId());


            return Response.yes(processInstance);
        } catch (Exception e) {
            log.error("ActivityService[]getProcessDefinition[]userParam:{}cause:{}", userParam, e);
            return Response.no(ErrorCode.START_IS_FAILED.getMessage());
        }

    }
@Override
public Response getTaskByProcessInstanceId(String processInstanceId){
    log.info("ActivityService[]getProcessInstanceIdByTask[]processInstanceId:{}", processInstanceId);

    try {
        final org.activiti.engine.task.Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        return Response.yes(task);
    } catch (Exception e) {
        log.error("ActivityService[]getProcessInstanceIdByTask[]processInstanceId:{}cause:{}", processInstanceId,e);
        return Response.no(ErrorCode.QUERY_IS_FAILED.getMessage());
    }

}
    @Override
    public Response taskProcessList(TaskQueryParam taskQueryParam, Authentication authentication) {
        log.info("ActivitiServiceImpl[]taskProcessList[]taskQueryParam:{}authentication:{}", taskQueryParam,authentication);
        Pageable pageable = null;
        if (!Objects.isNull(taskQueryParam.getStartNum()) && !Objects.isNull(taskQueryParam.getEndNum())) {
            pageable = Pageable.of(taskQueryParam.getStartNum(), taskQueryParam.getEndNum());
        }
        final GetTasksPayload tasksPayload = new GetTasksPayload();
        if (StringUtils.isNotEmpty(taskQueryParam.getProcessInstanceId())) {
            tasksPayload.setProcessInstanceId(taskQueryParam.getProcessInstanceId());
        }
        try {
            final TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(authentication.getName());
            if (Objects.isNull(pageable)) {
                pageable = Pageable.of(0, (int) taskQuery.count());
            }
            final Page<Task> tasks = taskRuntime.tasks(pageable, tasksPayload);
            final List<Task> content = tasks.getContent();
            final List<Task> collect = content.stream().filter(iter -> {
                if (StringUtils.isNotEmpty(taskQueryParam.getBusinessKey())) {
                    if (!taskQueryParam.getBusinessKey().equals(iter.getBusinessKey())) {
                        return false;
                    }
                }
                return true;
            }).filter(iter -> {
                if (StringUtils.isNotEmpty(taskQueryParam.getTaskDefinitionKey())) {
                    if (!taskQueryParam.getTaskDefinitionKey().equals(iter.getTaskDefinitionKey())) {
                        return false;
                    }
                }
                return true;
            }).filter(iter -> {
                if (StringUtils.isNotEmpty(taskQueryParam.getId())) {
                    if (!taskQueryParam.getId().equals(iter.getId())) {
                        return false;
                    }
                }
                return true;
            }).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(collect)) {
                return null;
            }
            return Response.yes(collect);
        } catch (Exception e) {
            log.error("ActivitiServiceImpl[]taskProcessList[]taskQueryParam:{}authentication:{}cause:{}", taskQueryParam,authentication, e);
            return Response.no(ErrorCode.QUERY_IS_FAILED.getMessage());
        }
    }

    @Override
    public Response completeTaskWithGroup(String processInstanceId, Map<String,Object> param) {
        log.info("ActivitiServiceImpl[]completeTaskWithGroup[]processInstanceId:{}param:{}", processInstanceId,param);
        final org.activiti.engine.task.Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();

        try {
            //拾取任务
            taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
            //执行任务
            final Task complete = taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).withVariables(param).build());

            return Response.yes(complete);
        } catch (Exception e) {
            log.error("ActivitiServiceImpl[]completeTaskWithGroup[]processInstanceId:{}param:{}cause:{}", processInstanceId,param, e);
            return Response.no(ErrorCode.COMPLETE_TASK_IS_FAILED.getMessage());

        }
    }

    @Override
    public Response completeTaskWithAssignee(String processInstanceId, Map<String,Object> param) {
        log.info("ActivitiServiceImpl[]completeTaskWithAssignee[]processInstanceId:{}param:{}", processInstanceId,param);
        final org.activiti.engine.task.Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        try {
            //执行任务
            final Task complete = taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).withVariables(param).build());

            return Response.yes(complete);
        } catch (Exception e) {
            log.error("ActivitiServiceImpl[]completeTaskWithAssignee[]processInstanceId:{}param:{}cause:{}", processInstanceId,param, e);
            return Response.no(ErrorCode.COMPLETE_TASK_IS_FAILED.getMessage());

        }
    }

    @Override
    public Response backProcessInstance( String processInstanceId) {
        log.info("ActivitiServiceImpl[]backProcessInstance[]processInstanceId:{}",  processInstanceId);

        try {
            final org.activiti.engine.task.Task result;
            final Response taskByProcessInstanceId = getTaskByProcessInstanceId( processInstanceId);
            if (taskByProcessInstanceId.success()){
              result = ( org.activiti.engine.task.Task)taskByProcessInstanceId.getResult();
            }else{
                return taskByProcessInstanceId;
            }
            final Task task = taskRuntime.task(result.getId());
            ActivitiUtil.backProcess(task);
            return Response.yes();
        } catch (Exception e) {
            log.error("ActivitiServiceImpl[]backProcessInstance[]processInstanceId:{}cause:{}",  processInstanceId,e);

            return Response.no();
        }
    }
}