package com.lnsoft.activiti.service;

import com.lnsoft.activiti.param.TaskQueryParam;
import com.lnsoft.activiti.param.UserParam;
import com.lnsoft.activiti.response.Response;
import org.springframework.security.core.Authentication;

import java.util.Map;

/**
 * @author Louyp
 * @version 1.0
 * @data 2020/11/21 11:12
 */
public interface ActivitiService {
    public Response getProcessDefinition( Integer startNum, Integer endNum);

    public Response startProcess(UserParam userParam);

    Response taskProcessList(TaskQueryParam taskQueryParam, Authentication authentication);

    Response completeTaskWithGroup(String processInstanceId, Map<String,Object> param);

    Response completeTaskWithAssignee(String processInstanceId,Map<String,Object> param);

    Response backProcessInstance(String processInstanceId);

    Response getTaskByProcessInstanceId( String processInstanceId);
}
