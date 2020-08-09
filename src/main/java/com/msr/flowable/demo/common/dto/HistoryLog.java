package com.msr.flowable.demo.common.dto;

import lombok.Data;
import org.flowable.engine.task.Comment;

import java.util.Date;
import java.util.List;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 16:35
 */

@Data
public class HistoryLog {

    private String taskId;

    private String parentTaskId;

    private String name;

    private String processInstanceId;

    private String executionId;

    private Date createTime;

    private Date endTime;

    private String approveUserName;

    private List<Comment> comments;

}
