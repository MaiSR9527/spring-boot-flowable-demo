package com.msr.flowable.demo.controller;

import com.google.common.collect.Maps;
import com.msr.flowable.demo.common.SessionConst;
import com.msr.flowable.demo.common.dto.HistoryLog;
import com.msr.flowable.demo.common.dto.Result;
import com.msr.flowable.demo.common.dto.TaskBean;
import com.msr.flowable.demo.service.ApproveService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 17:07
 */

@Controller
@RequestMapping("/approve")
public class ApprovalController {

    @Autowired
    ApproveService approveService;

    @Autowired
    TaskService taskService;


    @RequestMapping(value = "/create.json")
    @ResponseBody
    public Result<String> create() {
        Map<String, Object> map = approveService.createLeave();
        String taskId = (String) map.get("taskId");
        return Result.success(taskId);
    }

    @RequestMapping(value = "/task/list.json")
    @ResponseBody
    public Result<List<TaskBean>> taskList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        String assignee = (String) request.getSession().getAttribute(SessionConst.SESSION_USERNAME_KEY);
        List<Task> tasks = taskService// 与任务相关的Service
                .createTaskQuery()// 创建一个任务查询对象
                .taskAssignee(assignee)
                .list();
        List<TaskBean> list = approveService.converTaskList(tasks);
        return Result.success(list);
    }


    @RequestMapping(value = "/handle/task.json")
    @ResponseBody
    public Result<String> handleTask(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        String taskId = request.getParameter("taskId");
        String name = request.getParameter("name");
        String message = request.getParameter("message");
        String submitType = request.getParameter("submitType");

        String tlMsg = request.getParameter("tlMsg");
        String tlApprove = request.getParameter("tlApprove");

        String hrMsg = request.getParameter("hrMsg");
        String hrApprove = request.getParameter("hrApprove");

        Map<String, Object> leaveParams = Maps.newHashMap();
        leaveParams.put("name", name);
        leaveParams.put("message", message);
        leaveParams.put("submitType", submitType);

        leaveParams.put("tlMsg", tlMsg);
        leaveParams.put("tlApprove", tlApprove);

        leaveParams.put("hrMsg", hrMsg);
        leaveParams.put("hrApprove", hrApprove);

        approveService.submitFrom(taskId, leaveParams);
        return Result.success("");
    }


    /**
     * 流程图
     * http://localhost:8066/approve/chart?taskId=5018
     *
     * @return
     */
    @RequestMapping(value = "/chart", method = RequestMethod.GET)
    public void flowChart(HttpServletResponse httpServletResponse, String taskId) {
        try {
            approveService.genProcessDiagram(httpServletResponse, taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/history", method = RequestMethod.GET)
    @ResponseBody
    public List<HistoryLog> history(String processId) {
        List<HistoryLog> list = null;
        try {
            list = approveService.historyDetail(processId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

