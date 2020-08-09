package com.litsoft.flowable.controller;

import com.alibaba.fastjson.JSON;
import com.litsoft.flowable.bussiness.applyorder.entity.ApplyOrder;
import com.litsoft.flowable.bussiness.applyorder.service.IApplyOrderService;
import com.litsoft.flowable.bussiness.person.entity.Person;
import com.litsoft.flowable.bussiness.person.service.IPersonService;
import com.litsoft.flowable.commons.enums.ProcessInstanceVariableEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 报销Controller
 */
@Api("报销流程业务")
@Controller
@RequestMapping(value = "expense")
public class ExpenseController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    IPersonService personService;
    @Autowired
    IApplyOrderService applyOrderService;

    /**
     * 添加报销
     * @param userId    用户Id
     * @param money     报销金额
     * @param descption 描述
     */
    @ApiOperation(value = "发起审批流程")
    @GetMapping(value = "add")
    @ResponseBody
    public String addExpense(String userId, int money, String descption) {
        Person selectPersonObj = new Person();
        selectPersonObj.setPersonName(userId);
        Person selectPersonResult = personService.selectOne(selectPersonObj);
        if(null==selectPersonResult){
            return "当前用户不存在";
        }

        //启动流程
        HashMap<String, Object> map = new HashMap<>();
        map.put("money", money);
        map.put("taskUser", userId);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);

        ApplyOrder applyOrder = new ApplyOrder();
        applyOrder.setApplyPersonName(userId);
        applyOrder.setMoney(money);
        applyOrder.setCreateTime(new Date());
        applyOrder.setRemark(descption);
        applyOrder.setProcessInstanceId(processInstance.getProcessInstanceId());
        runtimeService.setVariable(processInstance.getId(), ProcessInstanceVariableEnum.PROCESSSHEET.getCode(),applyOrder);
        runtimeService.setVariable(processInstance.getId(), ProcessInstanceVariableEnum.APPLYPERSON.getCode(),selectPersonResult);
        applyOrderService.add(applyOrder);
        return "提交成功.流程Id为：" + processInstance.getId();
    }

    /**
     * 获取审批管理列表
     */
    @ApiOperation(value = "获取本人代办任务列表")
    @GetMapping(value = "/list")
    @ResponseBody
    public String list(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        List<String>  contextList = new ArrayList<String>();
        for (Task task : tasks) {
            System.out.println(task.toString());
            contextList.add(task.toString());
        }
        if(contextList!=null){
            return JSON.toJSONString(contextList);
        }
        return "list is null";
    }

    /**
     * 获取组内代审批的任务管理列表
     */
    @ApiOperation(value = "获取组内代办流程任务列表")
    @GetMapping(value = "/grouplist")
    @ResponseBody
    public String grouplist(String userId) {
        List<Task>taskList = processEngine.getTaskService()//获取任务service
                .createTaskQuery()//创建查询对象
                .taskCandidateUser(userId)//参与者，组任务查询
                .list();
        List<String>  contextList = new ArrayList<String>();
        for (Task task : taskList) {
            System.out.println(userId+" : 组内可代办的任务："+task.toString());
            contextList.add(task.toString());
        }
        if(contextList!=null){
            return JSON.toJSONString(contextList);
        }
        return "list is null";
    }

    /**
     * 任务转办其他人
     */
    @ApiOperation(value = "流程转办他人")
    @GetMapping(value = "/transferTo")
    @ResponseBody
    public String grouplist(String userId,String taskId,String acceptUserId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        taskService.setOwner(taskId, userId);
        taskService.setAssignee(taskId,acceptUserId );
        return "transferTo success";
    }

    /**
     * 批准
     * @param taskId 任务ID
     */
    @ApiOperation(value = "流程批准")
    @GetMapping(value = "apply")
    @ResponseBody
    public String apply(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }

        ApplyOrder applyOrder = JSON.parseObject(JSON.toJSONString(runtimeService.getVariable(task.getProcessInstanceId(),ProcessInstanceVariableEnum.PROCESSSHEET.getCode())),ApplyOrder.class);
        System.out.println("流程全局变量："+JSON.toJSONString(applyOrder));

        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        taskService.complete(taskId, map);
        return "processed ok!";
    }

    /**
     * 拒绝
     */
    @ApiOperation(value = "流程拒绝")
    @ResponseBody
    @GetMapping(value = "reject")
    public String reject(String taskId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete(taskId, map);
        return "reject";
    }

    /**
     * 生成流程图
     * @param processId 任务ID
     */
    @ApiOperation(value = "查看流程当前审批进度图")
    @GetMapping(value = "processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

        //流程走完的不显示图
        if (pi == null) {
            System.out.println("流程已走完毕");
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        OutputStream out = null;
        InputStream in =  diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows,  1.0,true);
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
