package com.msr.flowable.demo.service;


import com.msr.flowable.demo.common.dto.HistoryLog;
import com.msr.flowable.demo.common.dto.TaskBean;
import org.flowable.task.api.Task;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ApproveService {


    public Map<String, Object> createLeave();


    public List<TaskBean> converTaskList(List<Task> list);


    public Map<String, Object> getTaskFormPropertyList(String taskId);


    public void submitFrom(String taskId, Map<String, Object> leaveParams);

    /**
     * 流程图
     *
     * @param httpServletResponse
     * @param processId
     * @throws Exception
     */
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception;

    public List<HistoryLog> historyDetail(String processId);

}
