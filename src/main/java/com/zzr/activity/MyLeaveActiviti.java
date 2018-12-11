package com.zzr.activity;

import org.activiti.engine.*;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * 生成工作流表
 */
public class MyLeaveActiviti {
    public static void main(String[] args) {
        creatTable();
//        deployProcess();
//        startProcess();
//        queryTask();
    }

    /**
     * 生成工作流表
     */
    public static void creatTable(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("/spring/activiti.cfg.xml").buildProcessEngine();
    }

    /**
     * 部署流程
     */
    public static void deployProcess(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("/spring/activiti.cfg.xml").buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("VacationRequest.bpmn20.xml");//bpmn文件的名称
        builder.deploy();
    }

    /**
     * 启动流程
     */
    public static void startProcess(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("/spring/activiti.cfg.xml").buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        runtimeService.startProcessInstanceByKey("leave");//流程的名称，也可以使用ByID来启动流程
    }

    public static void queryTask(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("/spring/activiti.cfg.xml").buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();

        //根据assignee(代理人)查询任务
        String assignee = "student";
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
        int size = tasks.size();
        for (int i = 0; i < size; i++) {
            Task task = tasks.get(i);
        }
        //首次运行的时候这个没有输出，因为第一次运行的时候扫描act_ru_task的表里面是空的，但第一次运行完成之后里面会添加一条记录，之后每次运行里面都会添加一条记录
        for (Task task : tasks) {
            System.out.println("taskId:" + task.getId() +
                    ",taskName:" + task.getName() +
                    ",assignee:" + task.getAssignee() +
                    ",createTime:" + task.getCreateTime());
        }
    }
}
