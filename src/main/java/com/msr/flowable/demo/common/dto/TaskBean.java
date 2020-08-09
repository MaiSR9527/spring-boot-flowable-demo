package com.msr.flowable.demo.common.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 16:46
 */

@Data
public class TaskBean {

    private String taskId;

    private String name;

    private String processInstanceId;

    private String executionId;

    private String processDefinitionId;

    private Date createTime;

    private Date endTime;

    private String approveUserName;

}

