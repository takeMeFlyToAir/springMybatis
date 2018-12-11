package com.zzr.controller;

import com.zzr.common.JsonResp;
import com.zzr.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "findAllUser",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JsonResp findAllUser(){
        try {
            return new JsonResp(userService.findAllUser());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return new JsonResp(JsonResp.FAIL,"查询失败");
        }
    }
}
