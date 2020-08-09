package com.msr.flowable.demo.controller;

import com.msr.flowable.demo.service.MyService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/9 11:38
 */
@RestController
public class FlowableRestController {

    @Autowired
    private MyService myService;


    @ResponseBody
    @RequestMapping(value = "tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> taskList(@RequestParam String assignee) throws IllegalAccessException {
        List<Task> tasks = myService.taskListByAssignee(assignee);
        List<TaskRepresentation> list = new ArrayList<>();
        for (Task task : tasks) {
            String assignee1 = task.getAssignee();
            String name = task.getName();
            String id = task.getId();
            String processDefinitionId = task.getProcessDefinitionId();
            String processInstanceId = task.getProcessInstanceId();
            TaskRepresentation taskRepresentation = new TaskRepresentation(id, name, assignee1, processDefinitionId, processInstanceId);
            list.add(taskRepresentation);
        }
        return list;
    }

    @PostMapping(value = "/process")
    public void startProcessInstance(@RequestBody StartProcessRepresentation startProcessRepresentation) {
        myService.startProcess(startProcessRepresentation.getAssignee());
    }

    static class StartProcessRepresentation {
        private String assignee;

        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }
    }

}

@AllArgsConstructor
@Data
class TaskRepresentation {

    private String id;
    private String name;
    private String assignee;
    private String processDefinitionId;
    private String processInstanceId;
}