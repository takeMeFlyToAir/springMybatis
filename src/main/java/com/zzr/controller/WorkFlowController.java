package com.zzr.controller;

import com.zzr.common.JsonResp;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaozhirong on 2018/12/3.
 */
@Slf4j
@Controller
@RequestMapping(value = "workFlow")
public class WorkFlowController {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "deploy",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JsonResp deployWf(@RequestParam(value = "flowBpmnName")String flowBpmnName){
        try {
            repositoryService
                    .createDeployment()
                    .addClasspathResource(flowBpmnName)
                    .deploy();
            return new JsonResp("部署成功");
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new JsonResp(JsonResp.FAIL,"部署失败");
        }
    }

    @RequestMapping(value = "startFlow",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JsonResp startFlow(@RequestParam(value = "processKey")String processKey,
                              @RequestParam(value = "businessKey")String businessKey){
        try {
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("name","zzr");
            runtimeService.startProcessInstanceByKey(processKey,businessKey,paramMap);
//            runtimeService.startProcessInstanceByKey(processKey);
            return new JsonResp("启动成功");
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new JsonResp(JsonResp.FAIL,"启动失败");
        }
    }

    /**
     * 按分组信息查待办
     * @param group
     * @return
     */
    @RequestMapping(value = "queryTaskByGroup",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JsonResp queryTaskByGroup(@RequestParam(value = "group")String group){
        try {
            List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(group).list();
            System.out.println("dddddddd");
            return new JsonResp(dealTask(tasks));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new JsonResp(JsonResp.FAIL,"查询任务失败");
        }
    }

    /**
     * 按用户信息查待办
     * @param user
     * @return
     */
    @RequestMapping(value = "queryTaskByUser",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JsonResp queryTaskByUser(@RequestParam(value = "user")String user){
        try {
            List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(user).list();
            return new JsonResp(dealTask(tasks));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new JsonResp(JsonResp.FAIL,"查询任务失败");
        }
    }

    /**
     * 按assignee信息查待办
     * @param assignee
     * @return
     */
    @RequestMapping(value = "queryTaskByAssignee",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JsonResp queryTaskByAssignee(@RequestParam(value = "assignee")String assignee){
        try {
            List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
            return new JsonResp(dealTask(tasks));
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new JsonResp(JsonResp.FAIL,"查询任务失败");
        }
    }

    private List<Map> dealTask(List<Task> tasks){
        List<Map> resultList = new ArrayList<Map>();
        if(tasks != null){
            for (Task task : tasks){
                List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().includeProcessVariables().processInstanceId(task.getProcessInstanceId()).list();
                if(processInstanceList != null &&processInstanceList.size() > 0){
                    ProcessInstance processInstance = processInstanceList.get(0);
                    System.out.println(processInstance.getBusinessKey());
                    System.out.println(processInstance.getProcessVariables());
                }
                Map<String,Object> result = new HashMap<String, Object>();
                result.put("taskId",task.getId());
                result.put("taskName",task.getName());
                result.put("taskAssignee",task.getAssignee());
                resultList.add(result);
            }
        }
        return resultList;
    }

    @RequestMapping(value = "complete",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JsonResp complete(@RequestParam(value = "taskId")String taskId,
                             @RequestParam(value = "money")Integer money){
        try {
            Map<String,Object> paramMap = new HashMap<String,Object>();
            paramMap.put("money",money);
            taskService.complete(taskId,paramMap);
            return new JsonResp("处理成功");
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new JsonResp(JsonResp.FAIL,"处理失败");
        }
    }
}
