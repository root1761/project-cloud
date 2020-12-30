package com.lnsoft.activiti;

import com.lnsoft.activiti.security.UserSecurityUtil;
import com.lnsoft.activiti.util.ActivitiUtil;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.DeleteTaskPayloadBuilder;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.DeleteTaskPayload;
import org.activiti.api.task.model.payloads.GetTasksPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiApplicationTests {
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private UserSecurityUtil userSecurityUtil;
    @Autowired
    private ProcessRuntime processRuntime;

    @Test
    public void deploy() {
        ActivitiUtil.deploy("Bohui3", "bohui3");
    }

    @Test
    public void contextLoads() {
        userSecurityUtil.logInAs("salaboy");
        //查询所有流程定义信息
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        System.out.println("当前流程定义的数量：" + processDefinitionPage.getTotalItems());
        //获取流程信息
        for (ProcessDefinition processDefinition : processDefinitionPage.getContent()) {
            System.out.println("流程定义信息" + processDefinition);
        }
    }

//启动一个实例
@Test
public void testCompleteTask(){
        userSecurityUtil.logInAs("salaboy");
        String taskId="bec7bde7-2c71-11eb-b4e7-6431509aeee9";
    //拾取任务
              taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(taskId).build());
   // 执行任务
      final Task complete = taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskId).withVariable("userGroups2", "otherTeam").build());
    System.out.println(complete);

}

    @Test
    public void testsStartProcess() {
        userSecurityUtil.logInAs("salaboy");
        String processId = "springboot02";

        org.activiti.api.process.model.ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey(processId).withName(processId).withBusinessKey(processId).withVariable("fileContent", "activiti").withVariable("userGroups1","activitiTeam").build());
        System.out.println(processInstance);
        //     ProcessInstance{id='d5d9fdb5-2a52-11eb-a729-6431509aeee9', name='categorizeProcess', processDefinitionId='categorizeProcess:1:f18d5701-2a3f-11eb-84bd-6431509aeee9', processDefinitionKey='categorizeProcess', parentId='null', initiator='salaboy', startDate=Thu Nov 19 18:34:42 CST 2020, businessKey='categorizeProcess', status=COMPLETED, processDefinitionVersion='1'}System.out.println(processInstance);
    }

    @Test
    public void testBackProcess() throws Exception {
        userSecurityUtil.logInAs("salaboy");

        String taskId = "e7bd5b21-2ca3-11eb-a50f-6431509aeee9";
        final Task task = taskRuntime.task(taskId);
        System.out.println(task.getProcessInstanceId());
        //  final Task task = taskRuntime.task(taskId);
        //ActivitiUtil.backProcess(task);
//        taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
    }
@Test
public void  testSuspendProcessInstance(){
        userSecurityUtil.logInAs("salaboy");
        runtimeService.suspendProcessInstanceById("4ec16f33-32ae-11eb-9416-6431509aeee9");
}
    @Test
    public void testTaskService() {
        userSecurityUtil.logInAs("salaboy");
//指定组内任务人
    //    final List<org.activiti.engine.task.Task> taskQuery = taskService.createTaskQuery().taskDefinitionKey("_3").taskCandidateOrAssigned("ryandawsonuk").list();
   //     System.out.println(taskQuery);
        final GetTasksPayload getTasksPayload = new GetTasksPayload();
      //  getTasksPayload.setProcessInstanceId("dfd7183d-2b22-11eb-87b6-6431509aeee9");

        final Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 10),getTasksPayload);
        final List<Task> content = tasks.getContent();

        if (tasks.getTotalItems() > 0) {
            for (Task task : tasks.getContent()) {
                System.out.println("任务名称：" + task.getName());
                System.out.println("任务id" + task.getId());
                System.out.println(task.getProcessInstanceId());
                System.out.println(task.getProcessDefinitionId());
                System.out.println(task.getBusinessKey());
                System.out.println(task.getTaskDefinitionKey());
                System.out.println(task);
                //拾取任务
          //          taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
                //执行任务
              //  final Task complete = taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).withVariable("userGroups2", "otherTeam").build());
                //System.out.println(complete);
            }
        }
    }

    @Test
    public void deleteProInstance() {
        userSecurityUtil.logInAs("salaboy");
runtimeService.deleteProcessInstance("149320a1-2cae-11eb-a3f1-6431509aeee9","");
historyService.deleteHistoricProcessInstance("149320a1-2cae-11eb-a3f1-6431509aeee9");
    }
}
