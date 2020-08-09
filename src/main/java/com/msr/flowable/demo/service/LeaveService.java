package com.msr.flowable.demo.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.msr.flowable.demo.common.dto.HistoryLog;
import com.msr.flowable.demo.common.dto.TaskBean;
import com.msr.flowable.demo.config.event.CustomEventListener;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@Service
@Slf4j
public class LeaveService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    public Map<String, Object> createLeave() {
        // 流程启动
        String id = "leaveProcess";
        Map<String, Object> map = new HashMap<String, Object>();
        runtimeService.addEventListener(new CustomEventListener());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(id, map);

        log.info("----------创建流程ID------: {} ", processInstance.getId());

        String assignee = "el";// 当前任务办理人
        List<Task> tasks = taskService// 与任务相关的Service
                .createTaskQuery()// 创建一个任务查询对象
                .taskAssignee(assignee)
                .processInstanceId(processInstance.getId())
                .list();

        log.info("el的任务数量: {} ", tasks.size());
        Task task = tasks.get(0);
        return this.getTaskFormPropertyList(task.getId());
    }

    public List<TaskBean> converTaskList(List<Task> tasks) {
        List<TaskBean> list = Lists.newLinkedList();
        for (Task task : tasks) {
            TaskBean taskBean = this.converTask(task);
            list.add(taskBean);
        }
        return list;
    }

    private TaskBean converTask(Task task) {
        TaskBean taskBean = new TaskBean();
        taskBean.setTaskId(task.getId());
        taskBean.setName(task.getName());
        taskBean.setProcessInstanceId(task.getProcessInstanceId());
        taskBean.setExecutionId(task.getExecutionId());
        return taskBean;
    }

    public Map<String, Object> getTaskFormPropertyList(String taskId) {

        Map<String, Object> resultMap = Maps.newHashMap();

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        List<FormProperty> formProperties = taskFormData.getFormProperties();

        resultMap.put("taskId", task.getId());
        resultMap.put("properties", formProperties);
        return resultMap;
    }

    public void submitFrom(String taskId, Map<String, Object> leaveParams) {
        leaveParams.put("submitTime", new Date());
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        Map<String, Object> params = Maps.newHashMap();
        for (FormProperty property : formProperties) {
            params.put(property.getId(), leaveParams.get(property.getId()));
            taskService.addComment(taskId, null, property.getId() + ":" + leaveParams.get(property.getId()));//comment为批注内容
        }
        taskService.complete(taskId, params);
    }

    public void genProcessDiagram(HttpServletResponse httpServletResponse, String taskId) throws Exception {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processId = task.getProcessInstanceId();

        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

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
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows, engconf.getActivityFontName(), engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(), 1.0, true);
        OutputStream out = null;
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


    public List<HistoryLog> historyDetail(String processId) {
        List<HistoricTaskInstance> taskList = processEngine.getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(processId).orderByHistoricTaskInstanceStartTime().asc()
                .listPage(0, 100);

        List<HistoryLog> list = Lists.newLinkedList();

        for (HistoricTaskInstance task : taskList) {
            HistoryLog log = new HistoryLog();
            log.setParentTaskId(task.getParentTaskId());
            log.setTaskId(task.getId());
            log.setName(task.getName());
            log.setApproveUserName(task.getAssignee());
            List<Comment> comments = taskService.getTaskComments(task.getId());
            log.setComments(comments);
            list.add(log);
        }
        return list;
    }


}