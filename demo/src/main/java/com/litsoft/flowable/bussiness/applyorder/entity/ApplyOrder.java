package com.litsoft.flowable.bussiness.applyorder.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 报销申请流程单
 */
@Data
@Table(name = "bus_apply_order")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplyOrder implements Serializable {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer applyOrderId;

    @Column(name = "apply_person_name")
    private String applyPersonName;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Column(name = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @Column(name = "money")
    private Integer money;

    @Column(name = "remark")
    private String remark;
}
